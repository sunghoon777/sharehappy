package weShare.sharehappy.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class UserSummary {
    private String email;
    private String name;
    private Boolean isOrganization;
    private LocalDateTime regDate;
}
