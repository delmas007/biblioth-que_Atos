package ci.digitalacademy.bibliotheque.service.imp;

import ci.digitalacademy.bibliotheque.model.Book;
import ci.digitalacademy.bibliotheque.repository.BookRepository;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.impl.BookServiceImpl;
import ci.digitalacademy.bibliotheque.service.mapper.BookMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceImpTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;


    private Book book;
    private Book book1;
    private BookDTO bookDTO;
    private BookDTO bookDTO1;
    BookDTO bookToSave;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setup(){
        book = new Book(1L,"book-slug","Science Fiction","The Time Machine","H.G. Wells","A science fiction novel about time travel.",5,new ArrayList<>());
        book1 = new Book(16L,"book-slug11","Science Fiction11","The Time Machine11","H.G. Wells11","A science fiction novel about time travel11.",5,new ArrayList<>());
        bookDTO = new BookDTO(1L,"book-slug","Science Fiction","The Time Machine","H.G. Wells","A science fiction novel about time travel.",5,new ArrayList<>());
        bookDTO1 = new BookDTO(14L,"fff","ff","f","h","h",5,new ArrayList<>());
         bookToSave = new BookDTO(null,"fff","ff","f","h","h",5,new ArrayList<>());

    }

    @Test
    public void whenfindOneBySlug_thenReturnOptionalBook() {
        when(bookRepository.findOneBySlug("h_g_wells_826e7f61-6905-4157-9c32-79f518e72308")).thenReturn(Optional.of(book));
        when(bookMapper.fromEntity(book)).thenReturn(bookDTO);
        Optional<BookDTO> bookeDTO = bookService.findOneBySlug("h_g_wells_826e7f61-6905-4157-9c32-79f518e72308");
        Assertions.assertEquals(bookeDTO.get().getSlug(), bookDTO.getSlug());
    }

    @Test
    public void whenSave_thenReturnBook() {
        when(bookMapper.fromEntity(book1)).thenReturn(bookDTO1);
        when(bookMapper.toEntity(bookDTO1)).thenReturn(book1);
        when(bookRepository.save(book1)).thenReturn(book1);

        BookDTO bookDTO2 = bookService.save(bookDTO1);

        Assertions.assertEquals(bookDTO2.getId(), 14);
    }
}
