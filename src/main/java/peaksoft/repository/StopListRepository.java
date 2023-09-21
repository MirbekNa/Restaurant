package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.entity.StopList;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.dtoStopList.StopListResponse(s.id,s.reason,s.date) from StopList  s")
    Page<StopListResponse> getAllListStop(Pageable pageable);

}