package weShare.sharehappy.dto.findpassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordRequest2 {
    @NotNull
    private String first;
    @NotNull
    private String second;
    @NotNull
    private String third;
    @NotNull
    private String fourth;
    @NotNull
    private String fifth;

    @Override
    public String toString() {
        return first+second+third+fourth+fifth;
    }
}
