package ci.digitalacademy.bibliotheque;

import ci.digitalacademy.bibliotheque.security.AuthorityConstants;
import ci.digitalacademy.bibliotheque.service.RoleService;
import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.RoleDTO;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BibliothequeApplication implements CommandLineRunner {
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BibliothequeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.getAll().isEmpty()){
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRole(AuthorityConstants.ADMIN);
            RoleDTO roleDTO1 = new RoleDTO();
            roleDTO1.setRole(AuthorityConstants.USER);
            RoleDTO role =roleService.save(roleDTO);
            roleService.save(roleDTO1);
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("admin");
            userDTO.setPassword(bCryptPasswordEncoder.encode("admin"));
            userDTO.setRole(roleDTO);
            userService.save(userDTO);

        }
    }
}
