package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//    @Query("select new peaksoft.dto.dtoCategory.CategoryResponse(c.id,c.name) from Category c")
//    Page<CategoryResponse> getAllCategories(Pageable pageable);
//
//    @Query("select new peaksoft.dto.dtoCategory.CategoryResponse(c.id,c.name) from Category  c where c.name ilike  concat(:word,'%') " +
//            "or c.name ilike concat('%',:word,'%') ")
//    Page<CategoryResponse> search(@Param("word") String word,Pageable pageable);
}