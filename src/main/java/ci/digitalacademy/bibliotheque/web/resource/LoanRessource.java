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
    public ResponseEntity<LoanDTO> save(@RequestBody LoanDTO loanDTO){
        log.debug("REST Request to save Loan : {}", loanDTO);
        return new ResponseEntity<>(loanService.saveLoan(loanDTO), HttpStatus.CREATED);
    }

    @PostMapping("/reservation")
    @ApiResponse(responseCode = "201", description = "Reservation created")
    @Operation(summary = "Create a new reservation", description = "Create a new reservation")
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
}
