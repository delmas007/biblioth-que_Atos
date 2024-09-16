package ci.digitalacademy.bibliotheque.repository;

import ci.digitalacademy.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
