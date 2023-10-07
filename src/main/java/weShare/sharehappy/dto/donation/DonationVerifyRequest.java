package weShare.sharehappy.dto.donation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonationVerifyRequest {
    @NotNull
    private String orderId;
    @NotNull
    private String pgOrderId;
}
