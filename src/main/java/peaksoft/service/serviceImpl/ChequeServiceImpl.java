package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.entity.*;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.*;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository repository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final StopListRepository stopListRepository;
private final JdbcTemplate jdbcTemplate;


    public List<ChequeResponse> getAllCheques() {
        String sql = "SELECT c.id, u.first_name, u.last_name AS waiter_full_name, c.price_average, r.service, " +
                "(c.price_average * r.service / 100) + c.price_average AS grand_total, c.created_at " +
                "FROM cheques c " +
                "LEFT JOIN users u ON c.user_id = u.id " +
                "LEFT JOIN restaurants r ON u.restaurant_id = r.id " +
                "ORDER BY c.id";

        RowMapper<ChequeResponse> rowMapper = (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String waiterFullName = rs.getString("waiter_full_name");
            int priceAverage = rs.getInt("price_average");
            int service = rs.getInt("service");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            ChequeResponse chequeResponse = ChequeResponse.builder()
                    .id(id)
                    .waiterFullName(waiterFullName)
                    .priceAverage(priceAverage)
                    .service(service)
                    .grandTotal(rs.getInt("grand_total"))
                    .createdAt(createdAt)
                    .items(getChequeItems(id)) // Получение списка блюд
                    .build();

            return chequeResponse;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    private List<String> getChequeItems(Long chequeId) {
        String itemsSql = "SELECT mi.name AS item " +
                "FROM menu_items_cheques mic " +
                "JOIN menu_items mi ON mic.menu_items_id = mi.id " +
                "WHERE mic.cheques_id = ?";
        return jdbcTemplate.queryForList(itemsSql, String.class, chequeId);
    }



    @Override
    public int getTotalEarningsByWaiterForDay(Long waiterId, LocalDate date) {
        String sql = "SELECT SUM(mi.price * (100 + r.service) / 100) AS total_earnings " +
                "FROM cheques c " +
                "JOIN menu_items_cheques cmi ON c.id = cmi.cheques_id " +
                "JOIN menu_items mi ON cmi.menu_items_id = mi.id " +
                "JOIN restaurants r ON mi.restaurant_id = r.id " +
                "WHERE c.user_id = ? AND c.created_at::date = ?";

        Integer totalEarnings = jdbcTemplate.queryForObject(sql, Integer.class, waiterId, date);
        return totalEarnings != null ? totalEarnings : 0;
    }


    @Override
    public int getTotalEarningsForDay(Long restaurantId, LocalDate date) {
        String sql = "SELECT SUM(mi.price * (100 + r.service) / 100) AS total_earnings " +
                "FROM cheques c " +
                "JOIN menu_items_cheques cmi ON c.id = cmi.cheques_id " +
                "JOIN menu_items mi ON cmi.menu_items_id = mi.id " +
                "JOIN restaurants r ON mi.restaurant_id = r.id " +
                "WHERE c.created_at::date = ? " +
                "AND r.id = ?";
        Integer totalEarnings = jdbcTemplate.queryForObject(sql, Integer.class, date, restaurantId);
        return totalEarnings != null ? totalEarnings : 0;
    }



    @Override
    public SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not found...", userId)));
        LocalDate currentDate = LocalDate.now();

        List<StopList> stopLists = stopListRepository.findByDate(currentDate);

        for (Long menuItemId : chequeRequest.menuItemId()) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException(String.format("Item with id:%s is not found...", menuItemId)));

            if (isMenuItemInStopList(menuItem, stopLists) && menuItem.getCheques().isEmpty()) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("One or more items from the order are in the stop list for today")
                        .build();
            }
        }

        List<Cheque> cheques = new ArrayList<>();
        Cheque cheque = new Cheque();
        cheques.add(cheque);

        List<MenuItem> items = new ArrayList<>();
        for (Long menuItemId : chequeRequest.menuItemId()) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException(String.format("Item with id:%s is not found...", menuItemId)));
            menuItem.setCheques(cheques);
            items.add(menuItem);
        }

        int totalPrice = 0;
        for (MenuItem menuItem : items) {
            totalPrice += menuItem.getPrice();
        }

        cheque.setUser(user);
        cheque.setCreatedAt(LocalDate.now());
        cheque.setMenuItems(items);
        cheque.setPriceAverage(totalPrice);

        repository.save(cheque);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    private boolean isMenuItemInStopList(MenuItem menuItem, List<StopList> stopLists) {
        for (StopList stopList : stopLists) {
            if (stopList.getMenuItem().equals(menuItem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Cheque with id:%s is not present", id)));
        List <MenuItem> items = new ArrayList<>();
        for (Long l: chequeRequest.menuItemId()) {
            Optional<MenuItem> menuItem = menuItemRepository.findById(l);
            MenuItem menuItem1 = new MenuItem();
            menuItem1.setId(menuItem.get().getId());
            menuItem1.setName(menuItem.get().getName());
            menuItem1.setImage(menuItem.get().getImage());
            menuItem1.setRestaurant(menuItem.get().getRestaurant());
            menuItem1.setVegetarian(menuItem.get().isVegetarian());
            menuItem1.setPrice(menuItem.get().getPrice());
            items.add(menuItem1);

        }
        int totalPrice = 0;
        for (MenuItem m:items) {
            totalPrice+=m.getPrice();
        }
        cheque.setPriceAverage(totalPrice);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    @Override
    public ChequeResponse getChequeById(Long id) {
        Cheque cheque = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Cheque with id:%s is not present", id)));
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s is not present", 1L)));
      List<MenuItem> items =  cheque.getMenuItems();
        List<String> collect = items.stream().map(MenuItem::getName).toList();
        return ChequeResponse.builder()
                .id(cheque.getId())
                .waiterFullName(cheque.getUser().getFirstName())
                .items(collect)
                .service(restaurant.getService())
                .createdAt(cheque.getCreatedAt().atStartOfDay())
                .priceAverage(cheque.getPriceAverage())
                .grandTotal((cheque.getPriceAverage()* restaurant.getService()/100)+ cheque.getPriceAverage())
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
         return    SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully deleted")
                    .build();
        }else throw new NotFoundException(String.format("Cheque with id:%s is not present", id));
    }


}
;