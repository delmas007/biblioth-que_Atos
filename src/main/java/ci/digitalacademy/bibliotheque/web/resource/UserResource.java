package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug: {}", slug);
        return new ResponseEntity<>(userService.findOneBySlug(slug),HttpStatus.OK);
    }

    @GetMapping
    public List<UserDTO> findAll() {
        log.debug("REST request to get all");
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user){
        log.debug("REST request to save: {}", user);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public UserDTO update(@RequestBody UserDTO user, @PathVariable Long id){
        log.debug("REST request to update: {}", user);
        return userService.update(user, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.debug("REST request to delete: {}", id);
        userService.deleteById(id);
    }
}
