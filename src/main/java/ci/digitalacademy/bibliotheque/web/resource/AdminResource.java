package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.BookService;
import ci.digitalacademy.bibliotheque.service.LoanService;
import ci.digitalacademy.bibliotheque.service.UserService;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.dto.ConfirmLoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;
import ci.digitalacademy.bibliotheque.service.dto.UserDTO;
import ci.digitalacademy.bibliotheque.utils.SlugifyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminResource {

    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;

    @PatchMapping("/confirm-loan")
    public ResponseEntity<Boolean> confirmLoan(@RequestBody ConfirmLoanDTO confirmLoanDTO){
        log.debug("REST Request to confirm Loan : {}", confirmLoanDTO);
        boolean confirm = loanService.confirmLoan(confirmLoanDTO);
        if (confirm){
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(confirm,HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/rejete/{slug}")
    @ApiResponse(responseCode = "200", description = "Request to reject reservation")
    @Operation(summary = "Reject reservation", description = "Reject reservation")
    public ResponseEntity<Boolean> REJETE(@PathVariable String slug){
        log.debug("REST Request to confirm return : {}", slug);
        boolean rejete = loanService.REJETE(slug);
        if (rejete){
            return new ResponseEntity<>(rejete, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(rejete,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-loan")
    @ApiResponse(responseCode = "200", description = "Request to get all loan")
    @Operation(summary = "Get all loan", description = "Get all loan")
    public List<LoanDTO> getAllLoan(){
        log.debug("REST Request to get all loan");
        return loanService.getAllLoan();
    }

    @PatchMapping("/confirm-return/{slug}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request to confirm return"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    @Operation(summary = "Confirm return", description = "Confirm return")
    public ResponseEntity<Boolean> confirmReturn(@PathVariable String slug){
        log.debug("REST Request to confirm return : {}", slug);
        boolean confirm = loanService.confirmReturn(slug);
        if (confirm){
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(confirm,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/loan")
    @ApiResponse(responseCode = "201", description = "Loan created")
    @Operation(summary = "Create a new loan", description = "Create a new loan")
    public ResponseEntity<?> save(@RequestBody LoanDTO loanDTO){
        log.debug("REST Request to save Loan : {}", loanDTO);
        LoanDTO loanDTO1 =loanService.saveLoan(loanDTO);
        if (loanDTO1 == null){
            return new ResponseEntity<>("Emprunt already taken by the user",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loanDTO1, HttpStatus.CREATED);
    }

    @PostMapping("/book")
    @ApiResponse(responseCode = "201", description = "Book created")
    @Operation(summary = "Create a new book", description = "Create a new book")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO book){
        log.debug("REST request to save: {}", book);
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }


    @PutMapping("/update-book")
    @ApiResponse(responseCode = "200", description = "Book updated")
    @Operation(summary = "Update an existing book", description = "Update an existing book")
    public BookDTO update(@RequestBody BookDTO book){
        log.debug("REST request to update: {}", book);
        return bookService.update(book);
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

    @GetMapping("/all-book")
    @ApiResponse(responseCode = "200", description = "Request to get book")
    @Operation(summary = "Get all books", description = "Get all books")
    public List<BookDTO> getAll(){
        log.debug("REST request to get all");
        return bookService.findAll();
    }

    @DeleteMapping("/delete-book/{id}")
    @ApiResponse(responseCode = "200", description = "Request to get book")
    @Operation(summary = "Get book by id", description = "Get book by id")
    public void deleteById(@PathVariable Long id){
        log.debug("REST request to delete: {}", id);
        bookService.deleteById(id);
    }

    @PostMapping("/users")
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
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO user){
        log.debug("REST Request to update User : {}", user);
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @GetMapping("/all-users")
    @ApiResponse(responseCode = "200", description = "Request to get all users")
    @Operation(summary = "Get all users", description = "Get all users")
    public List<UserDTO> getAllUsers(){
        log.debug("REST Request to get all users");
        return userService.getAll();
    }

    @GetMapping("/user/slug/{slug}")
    public ResponseEntity<?> getOneUserBySlug(@PathVariable String slug){
        log.debug("REST Request to get User : {}", slug);
        Optional<UserDTO> oneById = userService.findOneBySlug(slug);
        if (oneById.isPresent()){
            return new ResponseEntity<>(oneById.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
