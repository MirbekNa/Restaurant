package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

SubCategory findByName(String name);
}