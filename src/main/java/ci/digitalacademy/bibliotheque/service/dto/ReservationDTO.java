package ci.digitalacademy.bibliotheque.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservationDTO {
    private Date date_loan;
    private Date deadline;
    private Boolean reservation;
    private UserDTO user;
    private BookDTO book;
}
