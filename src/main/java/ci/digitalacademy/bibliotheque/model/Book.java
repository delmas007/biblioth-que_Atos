package ci.digitalacademy.bibliotheque.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String category;
    private String title;
    private String author;
    private String description;
    private Integer quantite;

    @OneToMany(mappedBy = "book")
    private List<Loan> loan;
}
