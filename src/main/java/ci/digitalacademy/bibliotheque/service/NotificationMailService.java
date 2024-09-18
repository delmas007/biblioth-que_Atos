package ci.digitalacademy.bibliotheque.service;


import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;

public interface NotificationMailService {
    void sendNotificationMailReservation(LoanDTO loanDTO);
    void sendNotificationMailConfirmLoan(LoanDTO loanDTO);
    void sendNotificationMailLoan(LoanDTO loanDTO);
    void sendNotificationMailReturnConfirmation(LoanDTO loanDTO);
    void sendNotificationMailCancel(LoanDTO loanDTO);
    void sendNotificationMailRejete(LoanDTO loanDTO);
}
