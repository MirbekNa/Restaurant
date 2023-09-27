package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/cheque")
@RequiredArgsConstructor
public class ChequeAPI {
    private final ChequeService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping("/save")
    public SimpleResponse saveCheque(@RequestParam Long userId, @RequestBody ChequeRequest chequeRequest){
        return service.saveCheque(userId, chequeRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/total-earnings-for-day")
    public ResponseEntity<Integer> getTotalEarningsForDay(
            @RequestParam Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int totalEarnings = service.getTotalEarningsForDay(restaurantId, date);
        return ResponseEntity.ok(totalEarnings);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/total-earnings-by-waiter-for-day")
    public ResponseEntity<Integer> getTotalEarningsByWaiterForDay(
            @RequestParam Long waiterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int totalEarnings = service.getTotalEarningsByWaiterForDay(waiterId, date);
        return ResponseEntity.ok(totalEarnings);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateCheque(@PathVariable Long id,@RequestBody ChequeRequest chequeRequest){
        return service.updateCheque(id, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/getById/{id}")
    public ChequeResponse getById(@PathVariable Long id){
        return service.getChequeById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteById(@PathVariable Long id){
        return service.deleteCheque(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll")
    public List<ChequeResponse> getAll() {
        return service.getAllCheques();
    }

}
