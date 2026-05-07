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
public class ServiceItemResponse {

    private Long id;
    private Long subcategoryId;
    private String subcategoryName;
    private Long artisanId;
    private String artisanName;
    private String title;
    private String description;
    private BigDecimal price;
    private String unit;
    private Integer durationMinutes;
    private String imageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
