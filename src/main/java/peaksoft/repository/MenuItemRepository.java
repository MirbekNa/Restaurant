package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
   MenuItem findByName(String name);

}