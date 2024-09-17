package ci.digitalacademy.bibliotheque.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConfirmLoanDTO {
    private String slug;
    private Date deadline;
}
