package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.StopList;

public interface StopListRepository extends JpaRepository<StopList, Long> {

}