package ci.digitalacademy.bibliotheque.service.impl;

import ci.digitalacademy.bibliotheque.model.Book;
import ci.digitalacademy.bibliotheque.repository.BookRepository;
import ci.digitalacademy.bibliotheque.service.BookService;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.mapper.BookMapper;
import ci.digitalacademy.bibliotheque.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Optional<BookDTO> findOneById(Long id) {
        return bookRepository.findById(id).map(book -> bookMapper.fromEntity(book));

    }

    @Override
    public Optional<BookDTO> findOneBySlug(String slug) {
        return bookRepository.findOneBySlug(slug).map(bookMapper::fromEntity);

    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(book -> bookMapper.fromEntity(book)).toList();
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        bookDTO.setSlug(SlugifyUtils.generate(bookDTO.getAuthor()));
        Book book = bookMapper.toEntity(bookDTO);
        return bookMapper.fromEntity(bookRepository.save(book));
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
            return findOneById(bookDTO.getId()).map(existingBook -> {
                if (bookDTO.getCategory() != null) {
                    existingBook.setCategory(bookDTO.getCategory());
                }
                if (bookDTO.getTitle() != null) {
                    existingBook.setTitle(bookDTO.getTitle());
                }
                if (bookDTO.getAuthor() != null) {
                    existingBook.setAuthor(bookDTO.getAuthor());
                }
                if (bookDTO.getDescription() != null) {
                    existingBook.setDescription(bookDTO.getDescription());
                }
                if (bookDTO.getQuantite() != null) {
                    existingBook.setQuantite(bookDTO.getQuantite());
                }
                return save(existingBook);
            }).orElseThrow(IllegalArgumentException::new);    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);

    }
}
