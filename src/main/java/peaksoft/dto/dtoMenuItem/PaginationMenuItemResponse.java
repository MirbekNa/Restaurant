package peaksoft.dto.dtoMenuItem;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginationMenuItemResponse(List<MenuItemResponse> menuItemResponses,
                                         int currentPage,
                                         int pageSize) {
    public PaginationMenuItemResponse {
    }
}
