package ci.digitalacademy.bibliotheque.service.dto;

import ci.digitalacademy.bibliotheque.model.Book;
import ci.digitalacademy.bibliotheque.model.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoanDTO {
    private Long id;
    private Date date_loan;
    private Date deadline;
    private Boolean retun;
    private UserDTO user;
    private BookDTO book;
}
