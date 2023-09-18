package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
//    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price asc" )
//    Page<MenuItemResponse> getAllMenuItems(@Param("ascDesc") String ascDesc, Pageable pageable);
//    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price desc" )
//    Page<MenuItemResponse> getAllMenuItemsDesc(@Param("ascDesc") String ascDesc, Pageable pageable);
//
//    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m " +
//            "join m.subCategory s join s.category c where m.name ilike concat(:word,'%') or m.name ilike concat('%',:word,'%') or s.name ilike concat('%',:word,'%') or c.name ilike concat('%',:word,'%')")
//    Page<MenuItemResponse> search(@Param("word") String word, Pageable pageable);
//
//    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m " +
//            "where m.isVegetarian=:isVegetarian")
//    Page<MenuItemResponse> filterByIsVegetarian(@Param("isVegetarian") boolean isVegetarian,Pageable pageable);
}