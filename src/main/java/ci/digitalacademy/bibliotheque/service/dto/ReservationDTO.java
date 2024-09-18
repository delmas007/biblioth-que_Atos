package ci.digitalacademy.bibliotheque.service.dto;

import ci.digitalacademy.bibliotheque.model.enume.Statut;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservationDTO {
    private Date date_loan;
    private Date deadline;
    private Statut statut;
    private UserDTO user;
    private BookDTO book;
}
