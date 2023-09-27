package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NoSuchElementException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository repository;
    private final MenuItemRepository menuItemRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<StopListResponse> getAll(String ascDesc) {
        String sql = "SELECT s.id, s.reason, s.date, m.name as product_name FROM stopLists s " +
                "JOIN menu_items m ON s.menu_item_id = m.id " +
                "ORDER BY m.name " + ascDesc;

        RowMapper<StopListResponse> rowMapper = (rs, rowNum) -> StopListResponse.builder()
                .id(rs.getLong("id"))
                .reason(rs.getString("reason"))
                .date(rs.getDate("date").toLocalDate())
                .productName(rs.getString("product_name"))
                .build();

        return jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest) throws BadRequestException {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException(String.format("MenuItem with id:%s is not present", menuItemId)));
        List<StopList> all = repository.findAll();
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopList.setMenuItem(menuItem);
        if (all.isEmpty()){
            repository.save(stopList);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("StopList successfully saved")
                    .build();
        }
        for (StopList list : all) {

            if (list.getMenuItem().equals(menuItem) && list.getDate().equals(stopListRequest.date()) ) {
                throw new BadRequestException("MenuItem with this date already exist");
        }
    }
        repository.save(stopList);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("StopList successfully saved1")
                .build();
    }

    @Override
    public SimpleResponse updateStopList(Long id, StopListRequest stopListRequest) {
        StopList stopList = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Stop list with id:%s is not exist", id)));
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        repository.save(stopList);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public StopListResponse getStopListById(Long id) {
        StopList stopList = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Stop list with id:%s is not exist", id)));

        return StopListResponse.builder()
                .id(stopList.getId())
                .reason(stopList.getReason())
                .date(stopList.getDate())
                .build();
    }

    @Override
    public SimpleResponse deleteStopListById(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("successfully deleted")
                    .build();
        }else throw new NoSuchElementException(String.format("Stop list with id:%s is not exist", id));

    }
}
