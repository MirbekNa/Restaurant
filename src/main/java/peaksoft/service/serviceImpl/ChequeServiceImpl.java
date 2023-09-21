package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.AverageSumResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.dto.dtoCheque.PaginationChequeResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
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

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.getUserByEmail(name).orElseThrow(() -> new NotFoundException("User with email: " + name + " us bit found!"));
        return user;
    }

    @Override
    public PaginationChequeResponse getAllCheques() {
        return null;
    }

    @Override
    public SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not found...", userId)));
        Cheque cheque= new Cheque();
        List<Cheque> cheques = new ArrayList<>();
        cheques.add(cheque);
       List <MenuItem> items = new ArrayList<>();
        for (Long l: chequeRequest.menuItemId()) {
            MenuItem menuItem = menuItemRepository.findById(l).orElseThrow(() -> new NotFoundException(String.format("Item with id:%s is not found...", l)));
            menuItem.setCheques(cheques);
            items.add(menuItem);
        }
        int totalPrice = 0;
        for (MenuItem m:items) {
            totalPrice+=m.getPrice();
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
