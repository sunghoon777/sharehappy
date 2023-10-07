package weShare.sharehappy.dto.post;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
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
    @NotNull
    private BigDecimal targetAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
