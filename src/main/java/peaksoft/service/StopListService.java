package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.PaginationStopListResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.exceptions.BadRequestException;

public interface StopListService {
PaginationStopListResponse getAllStopLists(int currentPage,int pageSize);
SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest) throws BadRequestException;
SimpleResponse updateStopList(Long id, StopListRequest stopListRequest);
StopListResponse getStopListById(Long id);
SimpleResponse deleteStopListById(Long id);

}
