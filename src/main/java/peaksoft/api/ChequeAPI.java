package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.AverageSumResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;

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

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/averageSum")
//    public AverageSumResponse getAverageSum(@RequestParam LocalDate date){
//        return  service.getAverageSum(date);
//    }
//
//    @PreAuthorize("hasAuthority('WAITER')")
//    @GetMapping("/averageSumOfWaiter")
//    public AverageSumResponse getAverageSumOfWaiter(@RequestParam Long id, @RequestParam LocalDate dateTime){
//        return service.getAverageSumOfWaiter(id, dateTime);
//    }
}
