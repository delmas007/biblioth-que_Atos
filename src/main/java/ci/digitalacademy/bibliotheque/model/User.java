package ci.digitalacademy.bibliotheque.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "utilisateur")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String slug;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String city;
    private String country;
    private String street;

    @OneToMany(mappedBy = "user")
    private Set<Loan> loan;

    @ManyToOne
    private Role role;

    public User(long l, String slug, String firstName, String lastName, String email, String city) {

    }
}
