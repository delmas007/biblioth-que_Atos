package ci.digitalacademy.bibliotheque.service;

import ci.digitalacademy.bibliotheque.service.dto.ConfirmLoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    LoanDTO save(LoanDTO loanDTO);
    LoanDTO saveLoan(LoanDTO loanDTO);
    ReservationDTO saveReservation(ReservationDTO reservationDTO);
    boolean confirmLoan(ConfirmLoanDTO confirmLoanDTO);
    boolean confirmReturn(String slug);
    boolean cancel(String slug);
    boolean REJETE(String slug);
    List<LoanDTO> getAllReservation();
    List<LoanDTO> getAllLoan();
    LoanDTO update(LoanDTO loanDTO);
    void delete(Long id);
    List<LoanDTO> getAll();
    Optional<LoanDTO> findOneById(Long id);
    Optional<LoanDTO> findOneBySlug(String slug);
}
