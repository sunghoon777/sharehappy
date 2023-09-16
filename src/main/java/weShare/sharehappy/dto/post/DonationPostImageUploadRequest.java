package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DonationPostImageUploadRequest {
    private List<MultipartFile> imageFiles;
}
