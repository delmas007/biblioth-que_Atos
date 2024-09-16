package ci.digitalacademy.bibliotheque.service.mapper.imp;

import ci.digitalacademy.bibliotheque.model.Loan;
import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;
import ci.digitalacademy.bibliotheque.service.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapperImp implements LoanMapper {
    private final ModelMapper modelMapper;
    @Override
    public LoanDTO fromEntity(Loan entity) {
        return modelMapper.map(entity,LoanDTO.class);
    }

    @Override
    public Loan toEntity(LoanDTO dto) {
        return modelMapper.map(dto,Loan.class);
    }
}
