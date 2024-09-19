package ci.digitalacademy.bibliotheque.service.mapper.imp;

import ci.digitalacademy.bibliotheque.model.Role;
import ci.digitalacademy.bibliotheque.service.dto.RoleDTO;
import ci.digitalacademy.bibliotheque.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapperImp implements RoleMapper {
    private final ModelMapper modelMapper;
    @Override
    public RoleDTO fromEntity(Role entity) {
        return modelMapper.map(entity, RoleDTO.class);
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        return modelMapper.map(dto, Role.class);
    }
}
