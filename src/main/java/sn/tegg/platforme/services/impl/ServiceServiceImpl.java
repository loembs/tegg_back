package sn.tegg.platforme.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.tegg.platforme.data.models.ServiceCategory;
import sn.tegg.platforme.data.models.ServiceItem;
import sn.tegg.platforme.data.models.ServiceSubcategory;
import sn.tegg.platforme.data.repository.*;
import sn.tegg.platforme.services.ServiceService;
import sn.tegg.platforme.web.dto.response.ServiceCategoryResponse;
import sn.tegg.platforme.web.dto.response.ServiceItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceCategoryRepository categoryRepository;
    private final ServiceSubcategoryRepository subcategoryRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final ArtisanRepository artisanRepository;

    @Override
    public List<ServiceCategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::buildCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceCategoryResponse> getActiveCategories() {
        return categoryRepository.findByIsActive(true).stream()
                .map(this::buildCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCategoryResponse getCategoryById(Long id) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return buildCategoryResponse(category);
    }

    @Override
    public List<ServiceItemResponse> getServicesBySubcategory(Long subcategoryId) {
        return serviceItemRepository.findBySubcategoryId(subcategoryId).stream()
                .map(this::buildServiceItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceItemResponse> getServicesByArtisan(Long artisanId) {
        return List.of();
    }

    @Override
    public List<ServiceItemResponse> searchServices(String keyword) {
        return serviceItemRepository.findAll().stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        item.getNameFr().toLowerCase().contains(keyword.toLowerCase()) ||
                        (item.getDescription() != null && item.getDescription().toLowerCase().contains(keyword.toLowerCase())))
                .map(this::buildServiceItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceItemResponse getServiceById(Long id) {
        ServiceItem item = serviceItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return buildServiceItemResponse(item);
    }

    private ServiceCategoryResponse buildCategoryResponse(ServiceCategory category) {
        List<ServiceCategoryResponse.SubcategoryInfo> subcategories =
                subcategoryRepository.findByCategoryId(category.getId()).stream()
                        .map(sub -> ServiceCategoryResponse.SubcategoryInfo.builder()
                                .id(sub.getId())
                                .name(sub.getName())
                                .description(sub.getDescription())
                                .build())
                        .collect(Collectors.toList());

        return ServiceCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .iconUrl(category.getIcon())
                .sortOrder(category.getDisplayOrder())
                .isActive(category.getIsActive())
                .subcategories(subcategories)
                .build();
    }

    private ServiceItemResponse buildServiceItemResponse(ServiceItem item) {
        String subcategoryName = null;
        if (item.getSubcategoryId() != null) {
            var subOpt = subcategoryRepository.findById(item.getSubcategoryId());
            if (subOpt.isPresent()) {
                subcategoryName = subOpt.get().getName();
            }
        }

        return ServiceItemResponse.builder()
                .id(item.getId())
                .subcategoryId(item.getSubcategoryId())
                .subcategoryName(subcategoryName)
                .artisanId(null)
                .artisanName(null)
                .title(item.getName())
                .description(item.getDescription())
                .price(item.getEstimatedPrice() != null ? java.math.BigDecimal.valueOf(item.getEstimatedPrice()) : null)
                .unit(null)
                .durationMinutes(null)
                .imageUrl(null)
                .isActive(item.getIsActive())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
