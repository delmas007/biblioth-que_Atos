package ci.digitalacademy.bibliotheque.service.imp;

import ci.digitalacademy.bibliotheque.model.Book;
import ci.digitalacademy.bibliotheque.model.User;
import ci.digitalacademy.bibliotheque.repository.UserRepository;
import ci.digitalacademy.bibliotheque.service.RoleService;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.service.impl.UserServiceImpl;
import ci.digitalacademy.bibliotheque.service.mapper.RoleMapper;
import ci.digitalacademy.bibliotheque.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImpTest {
    @MockBean
    private  UserRepository userRepository;
    @MockBean
    private  UserMapper userMapper;
    @MockBean
    private  RoleService roleService;
    @MockBean
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    User user;
    UserDTO userDTO;

    @BeforeEach
    void setup(){
        user = new User(
                1L, "johndoe", "password123", "john-doe", "John", "Doe", "johndoe@example.com", "Abidjan", "CIV", "Rue des Palmiers", new HashSet<>(), null
        );
        user = new User(
                1L, "johndoe", "password123", "john-doe", "John", "Doe", "johndoe@example.com", "Abidjan", "CIV", "Rue des Palmiers", new HashSet<>(), null
        );
    }
//    public void whenSave_thenReturnUserDTO() {
//        // Given
//        //when
//        when(userMapper.fromEntity(user)).thenReturn(userDTO);
//        when(bookMapper.toEntity(bookDTO1)).thenReturn(book1);
//        UserDTO userDTO = userRepository.save(new UserDTO());
//    }
    }


