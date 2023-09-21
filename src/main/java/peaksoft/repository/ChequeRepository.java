package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.entity.Cheque;

import java.time.LocalDate;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("select new peaksoft.dto.dtoCheque.ChequeResponse(c.id,c.priceAverage) from Cheque c")
    Page<ChequeResponse> getAllCheques(Pageable pageable);

    @Query("select sum(c.priceAverage) from Cheque c join c.user u join u.cheques ch where u.id=:id and ch.createdAt=:date")
    int averageSumOfWaiter(@Param("id") Long id,@Param("date") LocalDate date);
}