package ci.digitalacademy.bibliotheque.model;

import ci.digitalacademy.bibliotheque.model.enume.Statut;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private Date date_loan;
    private Date deadline;
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;
}
