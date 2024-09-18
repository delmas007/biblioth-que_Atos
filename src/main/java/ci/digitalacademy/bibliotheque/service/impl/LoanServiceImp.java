package ci.digitalacademy.bibliotheque.service.impl;

import ci.digitalacademy.bibliotheque.model.enume.Statut;
import ci.digitalacademy.bibliotheque.repository.LoanRepository;
import ci.digitalacademy.bibliotheque.service.BookService;
import ci.digitalacademy.bibliotheque.service.LoanService;
import ci.digitalacademy.bibliotheque.service.NotificationMailService;
import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.*;
import ci.digitalacademy.bibliotheque.service.mapper.LoanMapper;
import ci.digitalacademy.bibliotheque.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoanServiceImp implements LoanService {
    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;
    private final NotificationMailService notificationMailService;

    @Override
    public LoanDTO save(LoanDTO loanDTO) {
        return loanMapper.fromEntity(loanRepository.save(loanMapper.toEntity(loanDTO)));
    }

    @Override
    public LoanDTO saveLoan(LoanDTO loanDTO) {
        Optional<BookDTO> optionalBook = bookService.findOneById(loanDTO.getBook().getId());
        Optional<UserDTO> optionalUser = userService.findOneById(loanDTO.getUser().getId());
        if (optionalBook.isEmpty() || optionalUser.isEmpty()) {
            return null;
        }
        boolean hasActiveLoan = getAllLoan().stream()
                .anyMatch(loan -> loan.getUser().getId().equals(loanDTO.getUser().getId()) && loan.getStatut() == Statut.EMPRUNT);

        if (hasActiveLoan ) {
            return null;
        }
        LoanDTO newLoanDTO = extracted(loanDTO, optionalUser, optionalBook);

        notificationMailService.sendNotificationMailLoan(newLoanDTO);
        loanDTO.setSlug(SlugifyUtils.generate(String.valueOf(newLoanDTO.getDate_loan())));
        return save(newLoanDTO);
    }




    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Optional<BookDTO> oneById = bookService.findOneById(reservationDTO.getBook().getId());
        Optional<UserDTO> oneById1 = userService.findOneById(reservationDTO.getUser().getId());
        if (oneById.isEmpty() || oneById1.isEmpty()){
            return null;
        }
        boolean hasActiveLoan = getAllLoan().stream()
                .anyMatch(loan -> loan.getUser().getId().equals(reservationDTO.getUser().getId()) && loan.getStatut() == Statut.EMPRUNT);
        boolean hasActiveReservation = getAllReservation().stream()
                .anyMatch(loan -> loan.getUser().getId().equals(reservationDTO.getUser().getId()) && loan.getStatut() == Statut.RESERVE);
        if (hasActiveLoan || hasActiveReservation) {
            return null;
        }
        LoanDTO loanDTO = getLoanDTO(reservationDTO, oneById1, oneById);
        ReservationDTO reservationDTO1 = extracted(reservationDTO, oneById, oneById1, loanDTO);
        loanDTO.setDeadline(Date.from(ZonedDateTime.now().plus(1, ChronoUnit.WEEKS).toInstant()));
        loanDTO.setSlug(SlugifyUtils.generate(String.valueOf(loanDTO.getDate_loan())));
        save(loanDTO);
        notificationMailService.sendNotificationMailReservation(loanDTO);
        return reservationDTO1;
    }

    private static ReservationDTO extracted(ReservationDTO reservationDTO, Optional<BookDTO> oneById, Optional<UserDTO> oneById1, LoanDTO loanDTO) {
        reservationDTO.setDate_loan(Date.from(Instant.now()));
        reservationDTO.setStatut(Statut.RESERVE);
        reservationDTO.setBook(oneById.get());
        reservationDTO.setUser(oneById1.get());
        reservationDTO.setDeadline(loanDTO.getDeadline());
        return reservationDTO;
    }


    @Override
    public boolean confirmLoan(ConfirmLoanDTO confirmLoanDTO) {
        boolean confirm = false;
        List<LoanDTO> all = getAll();
        for (LoanDTO loanDTO : all){
            if (loanDTO.getStatut() == Statut.RESERVE && loanDTO.getSlug().equals(confirmLoanDTO.getSlug())) {
                loanDTO.setStatut(Statut.EMPRUNT);
                loanDTO.setDate_loan(Date.from(Instant.now()));
                loanDTO.setDeadline(confirmLoanDTO.getDeadline());
                save(loanDTO);
                notificationMailService.sendNotificationMailConfirmLoan(loanDTO);
                confirm = true;
            }
        }
        return confirm;
    }


    @Override
    public boolean confirmReturn(String slug) {
        boolean confirm = false;
        List<LoanDTO> all = getAll();
        for (LoanDTO loanDTO : all){
            if (loanDTO.getStatut() ==Statut.EMPRUNT && loanDTO.getSlug().equals(slug)) {
                loanDTO.setStatut(Statut.RETOURNE);
                BookDTO bookDTO = loanDTO.getBook();
                bookDTO.setQuantite(bookDTO.getQuantite()+1);
                bookService.update(bookDTO);
                loanDTO.setBook(bookDTO);
                save(loanDTO);
                notificationMailService.sendNotificationMailReturnConfirmation(loanDTO);
                confirm = true;
            }
        }
        return confirm;
    }

    @Override
    public boolean cancel(String slug) {
        List<LoanDTO> allReservation = getAllReservation();
        for (LoanDTO loanDTO : allReservation){
            if ( loanDTO.getSlug().equals(slug)) {
                loanDTO.setStatut(Statut.ANNULE);
                save(loanDTO);
                notificationMailService.sendNotificationMailCancel(loanDTO);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean REJETE(String slug) {
        List<LoanDTO> allReservation = getAllReservation();
        for (LoanDTO loanDTO : allReservation){
            if ( loanDTO.getSlug().equals(slug)) {
                loanDTO.setStatut(Statut.REJETE);
                save(loanDTO);
                notificationMailService.sendNotificationMailRejete(loanDTO);
                return true;
            }
        }
        return false;
    }


    @Override
    public List<LoanDTO> getAllReservation() {
        List<LoanDTO> list = new ArrayList<>();
        List<LoanDTO> all = getAll();
        for (LoanDTO loanDTO : all){
            if (loanDTO.getStatut() == Statut.RESERVE){
                list.add(loanDTO);
            }
        }
        return list;
    }

    @Override
    public List<LoanDTO> getAllLoan() {
        List<LoanDTO> list = new ArrayList<>();
        List<LoanDTO> all = getAll();
        for (LoanDTO loanDTO : all){
            if (loanDTO.getStatut() == Statut.EMPRUNT){
                list.add(loanDTO);
            }
        }
        return list;
    }


    @Override
    public LoanDTO update(LoanDTO loanDTO) {
        return findOneById(loanDTO.getId()).map(existingLoan -> {
            if (loanDTO.getBook() != null) {
                existingLoan.setBook(loanDTO.getBook());
            }
            if (loanDTO.getDate_loan() != null) {
                existingLoan.setDate_loan(loanDTO.getDate_loan());
            }
            if (loanDTO.getDeadline() != null) {
                existingLoan.setDeadline(loanDTO.getDeadline());
            }
            if (loanDTO.getStatut() != null) {
                existingLoan.setStatut(loanDTO.getStatut());
            }
            return save(existingLoan);
        }).orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Loan : {}", id);
        loanRepository.deleteById(id);
    }

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll().stream().map(loan -> loanMapper.fromEntity(loan)).toList();
    }

    @Override
    public Optional<LoanDTO> findOneById(Long id) {
        return loanRepository.findById(id).map(loan -> loanMapper.fromEntity(loan));
    }

    @Override
    public Optional<LoanDTO> findOneBySlug(String slug) {
        return loanRepository.findOneBySlug(slug).map(loanMapper::fromEntity);
    }
    private LoanDTO getLoanDTO(ReservationDTO reservationDTO, Optional<UserDTO> oneById1, Optional<BookDTO> oneById) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setStatut(Statut.RESERVE);
        loanDTO.setBook(reservationDTO.getBook());
        loanDTO.setUser(reservationDTO.getUser());
        loanDTO.setDate_loan(Date.from(Instant.now()));
        loanDTO.setUser(oneById1.get());
        BookDTO book = oneById.get();
        book.setQuantite(book.getQuantite()-1);
        bookService.update(book);
        loanDTO.setBook(book);
        loanDTO.setDeadline(Date.from(ZonedDateTime.now().plus(1, ChronoUnit.WEEKS).toInstant()));
        return loanDTO;
    }
    private LoanDTO extracted(LoanDTO loanDTO, Optional<UserDTO> oneById1, Optional<BookDTO> oneById) {
        loanDTO.setStatut(Statut.EMPRUNT);
        loanDTO.setDate_loan(Date.from(Instant.now()));
        loanDTO.setDeadline(Date.from(ZonedDateTime.now().plus(1, ChronoUnit.WEEKS).toInstant()));
        loanDTO.setUser(oneById1.get());
        BookDTO book = oneById.get();
        book.setQuantite(book.getQuantite()-1);
        bookService.update(book);
        loanDTO.setBook(book);
        loanDTO.setStatut(Statut.EMPRUNT);
        loanDTO.setSlug(SlugifyUtils.generate(String.valueOf(loanDTO.getDate_loan())));
        return loanDTO;
    }
}
