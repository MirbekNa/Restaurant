package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.PaginationStopListResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.exceptions.BadRequestException;
import peaksoft.service.StopListService;

@RestController
@RequestMapping("/listStop")
@RequiredArgsConstructor
public class StopListAPI {
    private final StopListService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public PaginationStopListResponse getAllStopLists(@RequestParam int currentPage,@RequestParam int pageSize){
        return service.getAllStopLists(currentPage, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/save")
    public SimpleResponse saveStopList(@RequestParam Long menuItemID, @RequestBody StopListRequest stopListRequest) throws BadRequestException {
        return service.saveStopList(menuItemID, stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateStopList(@PathVariable Long id,@RequestBody StopListRequest stopListRequest){
        return service.updateStopList(id, stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/GetById/{id}")
    public StopListResponse getById(@PathVariable Long id){
        return service.getStopListById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteStopList(@PathVariable Long id){
        return service.deleteStopListById(id);
    }
}
