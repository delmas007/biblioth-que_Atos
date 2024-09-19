package ci.digitalacademy.bibliotheque.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {
    @Id
    private String role;

    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
    private Set<User> user;
}
