package ci.digitalacademy.bibliotheque.service.dto;

import ci.digitalacademy.bibliotheque.model.Book;
import ci.digitalacademy.bibliotheque.model.User;
import ci.digitalacademy.bibliotheque.model.enume.Statut;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoanDTO {
    private Long id;
    private String slug;
    private Date date_loan;
    private Statut statut;
    private Date deadline;
    private UserDTO user;
    private BookDTO book;
}
