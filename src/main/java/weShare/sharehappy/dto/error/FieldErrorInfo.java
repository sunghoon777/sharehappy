package weShare.sharehappy.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FieldErrorInfo {
    private String name;
    private String message;
}
