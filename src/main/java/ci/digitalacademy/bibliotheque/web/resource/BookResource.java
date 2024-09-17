package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.BookService;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.mapper.BookMapper;
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
@RequestMapping("/api/books")
@Slf4j
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;

    @ApiResponse(responseCode = "201", description = "Book created")
    @Operation(summary = "Create a new book", description = "Create a new book")
    @PostMapping
        public ResponseEntity<BookDTO> save(@RequestBody BookDTO book){
        log.debug("REST request to save: {}", book);
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Book updated")
    @Operation(summary = "Update an existing book", description = "Update an existing book")
    @PutMapping
    public BookDTO update(@RequestBody BookDTO book){
        log.debug("REST request to update: {}", book);
        return bookService.update(book);
    }

    @ApiResponse(responseCode = "200", description = "Request to get book")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
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
    @GetMapping
    public List<BookDTO> getAll(){
        log.debug("REST request to get all");
        return bookService.findAll();
    }

    @ApiResponse(responseCode = "200", description = "Request to get book")
    @Operation(summary = "Get book by id", description = "Get book by id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        log.debug("REST request to delete: {}", id);
        bookService.deleteById(id);
    }


}
