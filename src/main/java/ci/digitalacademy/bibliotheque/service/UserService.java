package ci.digitalacademy.bibliotheque.service;

import ci.digitalacademy.bibliotheque.service.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO,Long id);
    void deleteById(Long id);

    List<UserDTO> getAll();

    Optional<UserDTO> findOneById(Long id);
    Optional<UserDTO> findOneBySlug(String slug);

}
