package weShare.sharehappy.dto.findpassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordRequest1 {
    @NotEmpty
    private String email;
}
