package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.LoanService;
import ci.digitalacademy.bibliotheque.service.dto.ConfirmLoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Slf4j
@RequiredArgsConstructor
public class LoanRessource {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanDTO> save(@RequestBody LoanDTO loanDTO){
        log.debug("REST Request to save Loan : {}", loanDTO);
        return new ResponseEntity<>(loanService.saveLoan(loanDTO), HttpStatus.CREATED);
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservationDTO){
        log.debug("REST Request to save Reservation : {}", reservationDTO);
        return new ResponseEntity<>(loanService.saveReservation(reservationDTO), HttpStatus.CREATED);
    }

    @PostMapping("/confirm_loan")
    public ResponseEntity<Boolean> confirmLoan(@RequestBody ConfirmLoanDTO confirmLoanDTO){
        log.debug("REST Request to confirm Loan : {}", confirmLoanDTO);
        boolean confirm = loanService.confirmLoan(confirmLoanDTO);
        if (confirm){
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(confirm,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/confirm_return/{slug}")
    public ResponseEntity<Boolean> confirmReturn(@PathVariable String slug){
        log.debug("REST Request to confirm return : {}", slug);
        boolean confirm = loanService.confirmReturn(slug);
        if (confirm){
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(confirm,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reservation")
    public List<LoanDTO> getAllReservation(){
        log.debug("REST Request to get all reservation");
        return loanService.getAllReservation();
    }

    @GetMapping
    public List<LoanDTO> getAllLoan(){
        log.debug("REST Request to get all loan");
        return loanService.getAllLoan();
    }
}
