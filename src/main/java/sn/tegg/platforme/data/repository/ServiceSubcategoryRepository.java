package sn.tegg.platforme.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.tegg.platforme.data.models.ServiceSubcategory;

import java.util.List;

@Repository
public interface ServiceSubcategoryRepository extends JpaRepository<ServiceSubcategory, Long> {

    List<ServiceSubcategory> findByCategoryId(Long categoryId);

    List<ServiceSubcategory> findByIsActive(Boolean isActive);
}
