package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.service.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @ApiResponse(responseCode = "200", description = "Slog record")
    @Operation(summary = "record slug", description = "record user by slug")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug: {}", slug);
        return new ResponseEntity<>(userService.findOneBySlug(slug),HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "User record")
    @Operation(summary = "record user", description = "record user")
    @GetMapping
    public List<UserDTO> findAll() {
        log.debug("REST request to get all");
        return userService.getAll();
    }
    @ApiResponse(responseCode = "201", description = "User created")
    @Operation(summary = "created a new user", description = "Create a new user")
    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user){
        log.debug("REST request to save: {}", user);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "201", description = "User update")
    @Operation(summary = "update a user",description = "Update a user by id")
    @PutMapping("/{id}")
    public UserDTO update(@RequestBody UserDTO user, @PathVariable Long id){
        log.debug("REST request to update: {}", user);
        return userService.update(user, id);
    }

    @ApiResponse(responseCode = "200", description = "User delete")
    @Operation(summary = "delete a user", description = "Delete a user by id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.debug("REST request to delete: {}", id);
        userService.deleteById(id);
    }
}
