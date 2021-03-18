package team16.bankpaymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PCCRequestDTO {

    private String clientPan;
    private String securityNumber;
    private String cardHolderName;
    private String expirationDate;

    private Long merchantOrderId;
    private LocalDateTime merchantTimestamp;

    private Long paymentId;
    private Long paymentRequestId;

    private Long acquirerOrderId;
    private LocalDateTime acquirerTimestamp;

    private String merchantPan;
}
