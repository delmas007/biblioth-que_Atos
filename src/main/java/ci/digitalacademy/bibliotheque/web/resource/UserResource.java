package ci.digitalacademy.bibliotheque.web.resource;


import ci.digitalacademy.bibliotheque.service.BookService;
import ci.digitalacademy.bibliotheque.service.LoanService;
import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.dto.ReservationDTO;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.utils.SlugifyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;

    @PostMapping("/user")
    @ApiResponse(responseCode = "201", description = "User created")
    @Operation(summary = "Create a new user", description = "Create a new user")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user){
        log.debug("REST Request to save User : {}", user);
        user.setSlug(SlugifyUtils.generate(user.getFirstName()));
        return new  ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/update-user")
    @ApiResponse(responseCode = "200", description = "User updated")
    @Operation(summary = "Update an existing user", description = "Update an existing user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user){
        log.debug("REST Request to update User : {}", user);
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneUserBySlug(@PathVariable String slug){
        log.debug("REST Request to get User : {}", slug);
        Optional<UserDTO> oneById = userService.findOneBySlug(slug);
        if (oneById.isPresent()){
            return new ResponseEntity<>(oneById.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @ApiResponse(responseCode = "200", description = "Request to get book")
    @GetMapping("book/slug/{slug}")
    public ResponseEntity<?> getOneBookBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug: {}", slug);
        Optional<BookDTO> oneBySlug = bookService.findOneBySlug(slug);
        if (oneBySlug.isPresent()){
            return new ResponseEntity<>(oneBySlug.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponse(responseCode = "200", description = "Request to get book")
    @Operation(summary = "Get all books", description = "Get all books")
    @GetMapping("/all-book")
    public List<BookDTO> getAllBook(){
        log.debug("REST request to get all");
        return bookService.findAll();
    }

    @PostMapping("/reservation")
    @ApiResponse(responseCode = "201", description = "Reservation created")
    @Operation(summary = "Create a new reservation", description = "Create a new reservation")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO reservationDTO){
        log.debug("REST Request to save Reservation : {}", reservationDTO);
        ReservationDTO reservationDTO1 = loanService.saveReservation(reservationDTO);
        if (reservationDTO1 == null){
            return new ResponseEntity<>("Reservation or Emprunt already taken by the user",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservationDTO1, HttpStatus.CREATED);
    }

    @PatchMapping("/cancel/{slug}")
    @ApiResponse(responseCode = "200", description = "Request to cancel reservation")
    @Operation(summary = "Cancel reservation", description = "Cancel reservation")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable String slug){
        log.debug("REST Request to confirm return : {}", slug);
        boolean cancel = loanService.cancel(slug);
        if (cancel){
            return new ResponseEntity<>(cancel, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(cancel,HttpStatus.NOT_FOUND);
        }
    }
}
