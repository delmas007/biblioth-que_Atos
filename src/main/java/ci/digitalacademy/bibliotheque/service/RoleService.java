package ci.digitalacademy.bibliotheque.service;

import ci.digitalacademy.bibliotheque.service.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<RoleDTO> getRoleById(String role);
    RoleDTO save(RoleDTO roleDTO);
    RoleDTO update(RoleDTO roleDTO);
    void delete(Long id);
    List<RoleDTO> getAll();

}
