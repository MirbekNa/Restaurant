package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.StopList;

import java.time.LocalDate;
import java.util.List;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    List<StopList> findByDate(LocalDate date);
}