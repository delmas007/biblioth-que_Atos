package ci.digitalacademy.bibliotheque.service.dto;

import ci.digitalacademy.bibliotheque.model.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String slug;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String country;
    private String street;
    @JsonIgnore
    private Set<LoanDTO> loan;
    private RoleDTO role;
}
