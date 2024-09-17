package ci.digitalacademy.bibliotheque.web.resource;

import ci.digitalacademy.bibliotheque.service.BookService;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Slf4j
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
        public ResponseEntity<BookDTO> save(@RequestBody BookDTO book){
        log.debug("REST request to save: {}", book);
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BookDTO update(@RequestBody BookDTO book, @PathVariable Long id){
        log.debug("REST request to update: {}", book);
        return bookService.update(book, id);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug: {}", slug);
        return null;
    }

    @GetMapping
    public List<BookDTO> getAll(){
        log.debug("REST request to get all");
        return bookService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        log.debug("REST request to delete: {}", id);
        bookService.deleteById(id);
    }


}
