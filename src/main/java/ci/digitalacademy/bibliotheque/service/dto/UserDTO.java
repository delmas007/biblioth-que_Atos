package ci.digitalacademy.bibliotheque.service.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
