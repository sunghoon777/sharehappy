package weShare.sharehappy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationPostCommentUpdateRequest {
    @NotEmpty
    @Size(min = 1 ,max = 1000)
    private String content;
    @NotNull
    private Long commentId;
}
