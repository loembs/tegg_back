package sn.tegg.platforme.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.math.BigDecimal;
        import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestResponse {

    private Long id;
    private Long clientId;
    private String clientName;
    private Long serviceItemId;
    private String serviceTitle;
    private Long artisanId;
    private String artisanName;
    private String status;
    private String description;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal quotedPrice;
    private LocalDateTime scheduledDate;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
