package ci.digitalacademy.bibliotheque.service.impl;

import ci.digitalacademy.bibliotheque.model.User;
import ci.digitalacademy.bibliotheque.repository.UserRepository;
import ci.digitalacademy.bibliotheque.service.UserService;

import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.service.mapper.UserMapper;
import ci.digitalacademy.bibliotheque.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDTO save(UserDTO userDTO) {
        userDTO.setSlug(SlugifyUtils.generate(userDTO.getFirstName()));
        User user = userMapper.toEntity(userDTO);
        return userMapper.fromEntity(userRepository.save(user));
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        return findOneById(userDTO.getId()).map(existingUser ->{
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setCountry(userDTO.getCountry());
            existingUser.setCity(userDTO.getCity());
            existingUser.setStreet(userDTO.getStreet());
            return save(existingUser);

        }).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> {
            return userMapper.fromEntity(user);
        }).toList();
    }

    @Override
    public Optional<UserDTO> findOneById(Long id) {
        return userRepository.findById(id).map(book -> userMapper.fromEntity(book));
    }

    @Override
    public Optional<UserDTO> findOneBySlug(String slug) {
        return null;
    }
}
