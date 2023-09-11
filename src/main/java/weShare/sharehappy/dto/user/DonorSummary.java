package weShare.sharehappy.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class DonorSummary {
    private String email;
    private String nickname;
    private LocalDateTime regdate;

}
