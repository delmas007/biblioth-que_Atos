package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user){
        log.debug("REST Request to save User : {}", user);
        user.setSlug(SlugifyUtils.generate(user.getFirstName()));
        return new  ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO user){
        log.debug("REST Request to update User : {}", user);
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @GetMapping
    public List<UserDTO> getAll(){
        log.debug("REST Request to get all users");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable  Long id){
        log.debug("REST Request to get User : {}", id);
        Optional<UserDTO> oneById = userService.findOneById(id);
        if (oneById.isPresent()){
            return new ResponseEntity<>(oneById.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST Request to get User : {}", slug);
        Optional<UserDTO> oneById = userService.findOneBySlug(slug);
        if (oneById.isPresent()){
            return new ResponseEntity<>(oneById.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
