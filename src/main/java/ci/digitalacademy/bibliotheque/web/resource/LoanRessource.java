package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.LoanService;
import ci.digitalacademy.bibliotheque.service.dto.ConfirmLoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponse(responseCode = "201", description = "Loan created")
    @Operation(summary = "Create a new loan", description = "Create a new loan")
    public ResponseEntity<?> save(@RequestBody LoanDTO loanDTO){
        log.debug("REST Request to save Loan : {}", loanDTO);
        LoanDTO loanDTO1 =loanService.saveLoan(loanDTO);
        if (loanDTO1 == null){
                return new ResponseEntity<>("Emprunt already taken by the user",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loanDTO1, HttpStatus.CREATED);
    }

    @PostMapping("/reservation")
    @ApiResponse(responseCode = "201", description = "Reservation created")
    @Operation(summary = "Create a new reservation", description = "Create a new reservation")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO reservationDTO){
        log.debug("REST Request to save Reservation : {}", reservationDTO);
        ReservationDTO reservationDTO1 = loanService.saveReservation(reservationDTO);
        if (reservationDTO1 == null){
            return new ResponseEntity<>("Reservation or Emprunt already taken by the user",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservationDTO1, HttpStatus.CREATED);
    }

    @PatchMapping("/confirm_loan")
    public ResponseEntity<Boolean> confirmLoan(@RequestBody ConfirmLoanDTO confirmLoanDTO){
        log.debug("REST Request to confirm Loan : {}", confirmLoanDTO);
        boolean confirm = loanService.confirmLoan(confirmLoanDTO);
        if (confirm){
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(confirm,HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/confirm_return/{slug}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request to confirm return"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @Operation(summary = "Confirm return", description = "Confirm return")
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
    @ApiResponse(responseCode = "200", description = "Request to get all reservation")
    @Operation(summary = "Get all reservation", description = "Get all reservation")
    public List<LoanDTO> getAllReservation(){
        log.debug("REST Request to get all reservation");
        return loanService.getAllReservation();
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Request to get all loan")
    @Operation(summary = "Get all loan", description = "Get all loan")
    public List<LoanDTO> getAllLoan(){
        log.debug("REST Request to get all loan");
        return loanService.getAllLoan();
    }

    @PutMapping("/cancel/{slug}")
    @ApiResponse(responseCode = "200", description = "Request to cancel reservation")
    @Operation(summary = "Cancel reservation", description = "Cancel reservation")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable String slug){
        log.debug("REST Request to confirm return : {}", slug);
        boolean cancel = loanService.cancel(slug);
        if (cancel){
            return new ResponseEntity<>(cancel, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(cancel,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/rejete/{slug}")
    @ApiResponse(responseCode = "200", description = "Request to reject reservation")
    @Operation(summary = "Reject reservation", description = "Reject reservation")
    public ResponseEntity<Boolean> REJETE(@PathVariable String slug){
        log.debug("REST Request to confirm return : {}", slug);
        boolean rejete = loanService.REJETE(slug);
        if (rejete){
            return new ResponseEntity<>(rejete, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(rejete,HttpStatus.NOT_FOUND);
        }
    }

}
