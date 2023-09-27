package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.exceptions.BadRequestException;

import java.util.List;

public interface StopListService {
    List<StopListResponse>getAll(String ascDesc);
SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest) throws BadRequestException;
SimpleResponse updateStopList(Long id, StopListRequest stopListRequest);
StopListResponse getStopListById(Long id);
SimpleResponse deleteStopListById(Long id);

}
