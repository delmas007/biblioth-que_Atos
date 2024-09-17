package ci.digitalacademy.bibliotheque.service;

import ci.digitalacademy.bibliotheque.service.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDTO>findOneById(Long id);
    Optional<BookDTO>findOneBySlug(String slug);
    List<BookDTO> findAll();
    BookDTO save(BookDTO bookDTO);
    BookDTO update(BookDTO bookDTO, Long id);
    void deleteById(Long id);



}
