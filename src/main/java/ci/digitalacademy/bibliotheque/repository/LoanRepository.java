package ci.digitalacademy.bibliotheque.repository;

import ci.digitalacademy.bibliotheque.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
}
