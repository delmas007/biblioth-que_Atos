package ci.digitalacademy.bibliotheque.repository;

import ci.digitalacademy.bibliotheque.model.Loan;
import org.apache.commons.lang3.concurrent.UncheckedFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    Optional<Loan> findOneBySlug(String slug);
}
