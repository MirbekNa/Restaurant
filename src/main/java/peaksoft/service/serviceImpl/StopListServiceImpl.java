package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.PaginationStopListResponse;
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

    @Override
    public PaginationStopListResponse getAllStopLists(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<StopListResponse> allListStop = repository.getAllListStop(pageable);
        return PaginationStopListResponse.builder()
                .stopListResponses(allListStop.getContent())
                .currentPage(allListStop.getNumber()+1)
                .pageSize(allListStop.getTotalPages())
                .build();
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
