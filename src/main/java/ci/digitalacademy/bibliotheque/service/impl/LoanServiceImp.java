package ci.digitalacademy.bibliotheque.service.impl;

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
        loanDTO.setSlug(SlugifyUtils.generate(String.valueOf(loanDTO.getDate_loan())));
        return loanMapper.fromEntity(loanRepository.save(loanMapper.toEntity(loanDTO)));
    }

    @Override
    public LoanDTO saveLoan(LoanDTO loanDTO) {
        Optional<BookDTO> oneById = bookService.findOneById(loanDTO.getBook().getId());
        Optional<UserDTO> oneById1 = userService.findOneById(loanDTO.getUser().getId());
        if (oneById.isEmpty() || oneById1.isEmpty()){
            return null;
        }
        loanDTO.setRetun(false);
        loanDTO.setReservation(false);
        loanDTO.setDate_loan(Date.from(Instant.now()));
        loanDTO.setDeadline(Date.from(ZonedDateTime.now().plus(1, ChronoUnit.WEEKS).toInstant()));
        loanDTO.setUser(oneById1.get());
        BookDTO book =oneById.get();
        book.setQuantite(book.getQuantite()-1);
        bookService.update(book);
        loanDTO.setBook(book);
        loanDTO.setSlug(SlugifyUtils.generate(String.valueOf(loanDTO.getDate_loan())));
        notificationMailService.sendNotificationMailLoan(loanDTO);
        return save(loanDTO);
    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Optional<BookDTO> oneById = bookService.findOneById(reservationDTO.getBook().getId());
        Optional<UserDTO> oneById1 = userService.findOneById(reservationDTO.getUser().getId());
        if (oneById.isEmpty() || oneById1.isEmpty()){
            return null;
        }
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setReservation(true);
        loanDTO.setBook(reservationDTO.getBook());
        loanDTO.setUser(reservationDTO.getUser());
        loanDTO.setDate_loan(Date.from(Instant.now()));
        loanDTO.setRetun(false);
        loanDTO.setUser(oneById1.get());
        BookDTO book =oneById.get();
        book.setQuantite(book.getQuantite()-1);
        bookService.update(book);
        loanDTO.setBook(book);
        loanDTO.setDeadline(Date.from(ZonedDateTime.now().plus(1, ChronoUnit.WEEKS).toInstant()));
        reservationDTO.setDate_loan(Date.from(Instant.now()));
        reservationDTO.setReservation(true);
        reservationDTO.setBook(oneById.get());
        reservationDTO.setUser(oneById1.get());
        reservationDTO.setDeadline(loanDTO.getDeadline());
        loanDTO.setDeadline(Date.from(ZonedDateTime.now().plus(1, ChronoUnit.WEEKS).toInstant()));
        notificationMailService.sendNotificationMailReservation(loanDTO);
        save(loanDTO);
        return reservationDTO;
    }

    @Override
    public boolean confirmLoan(ConfirmLoanDTO confirmLoanDTO) {
        boolean confirm = false;
        List<LoanDTO> all = getAll();
        for (LoanDTO loanDTO : all){
            if (loanDTO.getReservation() && loanDTO.getUser().getSlug().equals(confirmLoanDTO.getSlug())){
                loanDTO.setReservation(false);
                loanDTO.setDate_loan(Date.from(Instant.now()));
                loanDTO.setDeadline(confirmLoanDTO.getDeadline());
                loanDTO.setRetun(false);
                save(loanDTO);
                notificationMailService.sendNotificationMailLoan(loanDTO);
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
            if (!loanDTO.getReservation() && !loanDTO.getRetun() && loanDTO.getUser().getSlug().equals(slug)) {
                loanDTO.setRetun(true);
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
    public List<LoanDTO> getAllReservation() {
        List<LoanDTO> list = new ArrayList<>();
        List<LoanDTO> all = getAll();
        for (LoanDTO loanDTO : all){
            if (loanDTO.getReservation()){
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
            if (!loanDTO.getReservation() && !loanDTO.getRetun()){
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
            if (loanDTO.getRetun() != null) {
                existingLoan.setRetun(loanDTO.getRetun());
            }
            if (loanDTO.getReservation() != null) {
                existingLoan.setReservation(loanDTO.getReservation());
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
}
