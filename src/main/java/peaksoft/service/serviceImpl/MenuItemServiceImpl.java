package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.StopList;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.NoSuchElementException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final StopListRepository stopListRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MenuItemResponse> getAllMenuItems() {
        String sql = "SELECT id, name, image, price, description, is_vegetarian " +
                "FROM menuItems ";

        RowMapper<MenuItemResponse> rowMapper = (rs, rowNum) -> MenuItemResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .image(rs.getString("image"))
                .price(rs.getInt("price"))
                .description(rs.getString("description"))
                .isVegetarian(rs.getBoolean("is_vegetarian"))
                .build();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId, Long suvCategoryId, MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NoSuchElementException(String.format("Restaurant with id:%s does not exist", restaurantId)));
        SubCategory subCategory = subCategoryRepository.findById(suvCategoryId).orElseThrow(() -> new NoSuchElementException(String.format("SubCategory with id:%s does not exist", suvCategoryId)));
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setRestaurant(restaurant);
        menuItem.setSubCategory(subCategory);
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        repository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved...")
                .build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("MenuItem with id:%s does not exist", id)));
        menuItem.setName(menuItemRequest.name());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        repository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved...")
                .build();
    }

    @Override
    public MenuItemResponse getMenuItemById(Long id) {
        MenuItem menuItem = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("MenuItem with id:%s does not exist", id)));
        List<StopList> all = stopListRepository.findAll();
        for (StopList s: all) {
            if (s.getMenuItem().equals(menuItem) && s.getDate().equals(LocalDate.now())){
                return MenuItemResponse.builder()
                        .name("This menuItem temporarily no available")
                        .build();
            }
        }
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .image(menuItem.getImage())
                .isVegetarian(menuItem.isVegetarian())
                .build();
    }

    @Override
    public SimpleResponse deleteMenuItem(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return  SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully deleted...")
                    .build();
        }else throw new  NoSuchElementException(String.format("MenuItem with id:%s does not exist", id));

    }


    @Override
    public List<MenuItemResponse> searchByName(String word) {
        String sql = "SELECT id, name, image, price, description, is_vegetarian " +
                "FROM menu_items " +
                "WHERE name ILIKE :word";

        RowMapper<MenuItemResponse> rowMapper = (rs, rowNum) -> MenuItemResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .image(rs.getString("image"))
                .price(rs.getInt("price"))
                .description(rs.getString("description"))
                .isVegetarian(rs.getBoolean("is_vegetarian"))
                .build();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("word", "%" + word + "%");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public List<MenuItemResponse> filterByIsVegetarian(boolean isVegetarian) {
        String sql = "SELECT id, name, image, price, description, is_vegetarian " +
                "FROM menu_items " +
                "WHERE is_vegetarian = :isVegetarian";

        RowMapper<MenuItemResponse> rowMapper = (rs, rowNum) -> MenuItemResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .image(rs.getString("image"))
                .price(rs.getInt("price"))
                .description(rs.getString("description"))
                .isVegetarian(rs.getBoolean("is_vegetarian"))
                .build();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("isVegetarian", isVegetarian);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
}
