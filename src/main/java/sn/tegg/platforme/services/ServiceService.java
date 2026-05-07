package sn.tegg.platforme.services;

import sn.tegg.platforme.web.dto.response.ServiceCategoryResponse;
import sn.tegg.platforme.web.dto.response.ServiceItemResponse;

import java.util.List;

public interface ServiceService {

    List<ServiceCategoryResponse> getAllCategories();

    List<ServiceCategoryResponse> getActiveCategories();

    ServiceCategoryResponse getCategoryById(Long id);

    List<ServiceItemResponse> getServicesBySubcategory(Long subcategoryId);

    List<ServiceItemResponse> getServicesByArtisan(Long artisanId);

    List<ServiceItemResponse> searchServices(String keyword);

    ServiceItemResponse getServiceById(Long id);
}
