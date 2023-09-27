package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ChequeService {
   List<ChequeResponse> getAllCheques();
    SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest);
    SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest);
    ChequeResponse getChequeById(Long id);
    SimpleResponse deleteCheque(Long id);
 int getTotalEarningsByWaiterForDay(Long waiterId, LocalDate date);
 int getTotalEarningsForDay(Long restaurantId, LocalDate date);

}
