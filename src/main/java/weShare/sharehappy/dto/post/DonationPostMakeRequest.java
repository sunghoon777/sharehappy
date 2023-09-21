package weShare.sharehappy.dto.post;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DonationPostMakeRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String category;
    @NotNull
    private MultipartFile thumbnail;
    @Positive
    @Min(value = 100000)
    private Long targetAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
