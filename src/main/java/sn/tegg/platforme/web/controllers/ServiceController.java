package sn.tegg.platforme.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.tegg.platforme.services.ServiceService;
import sn.tegg.platforme.web.dto.response.ApiResponse;
import sn.tegg.platforme.web.dto.response.ServiceCategoryResponse;
import sn.tegg.platforme.web.dto.response.ServiceItemResponse;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<ServiceCategoryResponse>>> getAllCategories() {
        List<ServiceCategoryResponse> categories = serviceService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", categories));
    }

    @GetMapping("/categories/active")
    public ResponseEntity<ApiResponse<List<ServiceCategoryResponse>>> getActiveCategories() {
        List<ServiceCategoryResponse> categories = serviceService.getActiveCategories();
        return ResponseEntity.ok(ApiResponse.success("Active categories retrieved successfully", categories));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<ServiceCategoryResponse>> getCategoryById(@PathVariable Long id) {
        ServiceCategoryResponse category = serviceService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", category));
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<ApiResponse<List<ServiceItemResponse>>> getServicesBySubcategory(
            @PathVariable Long subcategoryId) {
        List<ServiceItemResponse> services = serviceService.getServicesBySubcategory(subcategoryId);
        return ResponseEntity.ok(ApiResponse.success("Services retrieved successfully", services));
    }

    @GetMapping("/artisan/{artisanId}")
    public ResponseEntity<ApiResponse<List<ServiceItemResponse>>> getServicesByArtisan(
            @PathVariable Long artisanId) {
        List<ServiceItemResponse> services = serviceService.getServicesByArtisan(artisanId);
        return ResponseEntity.ok(ApiResponse.success("Services retrieved successfully", services));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ServiceItemResponse>>> searchServices(
            @RequestParam String keyword) {
        List<ServiceItemResponse> services = serviceService.searchServices(keyword);
        return ResponseEntity.ok(ApiResponse.success("Services retrieved successfully", services));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceItemResponse>> getServiceById(@PathVariable Long id) {
        ServiceItemResponse service = serviceService.getServiceById(id);
        return ResponseEntity.ok(ApiResponse.success("Service retrieved successfully", service));
    }
}
