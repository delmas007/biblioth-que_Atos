package ci.digitalacademy.bibliotheque.service.dto;

import ci.digitalacademy.bibliotheque.model.Loan;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String slug;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String country;
    private String street;
    private Set<LoanDTO> loan;
}
