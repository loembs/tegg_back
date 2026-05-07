package sn.tegg.platforme.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String iconUrl;
    private Integer sortOrder;
    private Boolean isActive;
    private List<SubcategoryInfo> subcategories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubcategoryInfo {
        private Long id;
        private String name;
        private String description;
    }
}
