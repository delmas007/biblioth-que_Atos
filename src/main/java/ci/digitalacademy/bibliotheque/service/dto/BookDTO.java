package ci.digitalacademy.bibliotheque.service.dto;

import ci.digitalacademy.bibliotheque.model.Loan;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {
    private Long id;
    private String slug;
    private String category;
    private String title;
    private String author;
    private String description;
    private Integer quantite;
    private List<LoanDTO> loan;
}
