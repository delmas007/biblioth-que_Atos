package ci.digitalacademy.bibliotheque.service.impl;

import ci.digitalacademy.bibliotheque.repository.RoleRepository;
import ci.digitalacademy.bibliotheque.service.RoleService;
import ci.digitalacademy.bibliotheque.service.dto.RoleDTO;
import ci.digitalacademy.bibliotheque.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImp implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Override
    public Optional<RoleDTO> getRoleById(String role) {
        return roleRepository.findByRole(role).map(roleMapper::fromEntity);
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        return roleMapper.fromEntity(roleRepository.save(roleMapper.toEntity(roleDTO)));
    }

    @Override
    public RoleDTO update(RoleDTO roleDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::fromEntity).toList();
    }
}
