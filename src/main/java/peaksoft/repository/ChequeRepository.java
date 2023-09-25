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

}