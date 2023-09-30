package weShare.sharehappy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonationPostCommentsRequest {
    @NotNull
    private Long postId;
    @NotNull
    @Min(value = 0)
    private Integer page;
}
