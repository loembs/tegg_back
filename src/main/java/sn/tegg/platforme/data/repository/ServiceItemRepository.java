package sn.tegg.platforme.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.tegg.platforme.data.models.ServiceItem;

import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {

    List<ServiceItem> findBySubcategoryId(Long subcategoryId);

    List<ServiceItem> findByCategoryId(Long categoryId);

    List<ServiceItem> findByIsActive(Boolean isActive);
}
