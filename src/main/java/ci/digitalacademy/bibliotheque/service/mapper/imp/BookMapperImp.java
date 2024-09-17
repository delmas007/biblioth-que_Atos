package ci.digitalacademy.bibliotheque.service.mapper.imp;

import ci.digitalacademy.bibliotheque.model.Book;
import ci.digitalacademy.bibliotheque.service.dto.BookDTO;
import ci.digitalacademy.bibliotheque.service.mapper.BookMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapperImp implements BookMapper {

    private  final ModelMapper modelMapper;

    @Override
    public BookDTO fromEntity(Book entity) {
        return modelMapper.map(entity, BookDTO.class);
    }

    @Override
    public Book toEntity(BookDTO dto) {
        return modelMapper.map(dto, Book.class);
    }



}
