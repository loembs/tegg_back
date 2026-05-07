package sn.tegg.platforme.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {

    @NotNull(message = "Service item ID is required")
    private Long serviceItemId;

    private String description;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String scheduledDate;
}
