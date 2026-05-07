package sn.tegg.platforme.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.tegg.platforme.data.models.Artisan;

import java.util.Optional;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisan, Long> {

    Optional<Artisan> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsByCinNumber(String cinNumber);
}
