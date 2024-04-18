package fr.isep.adopte_un_logement.repositories;

import fr.isep.adopte_un_logement.entities.Review;
import org.hibernate.query.JpaTuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    boolean existsByHousingId(UUID housingId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.housing.id = ?1")
    float getAverageRatingByHousingId(UUID housingId);

    Page<Review> findAllByHousingId(UUID housingId, Pageable pageable);

    @Query("SELECT r.housing.id, AVG(r.rating) FROM Review r WHERE r.housing.id IN ?1 GROUP BY r.housing.id")
    List<JpaTuple> getAverageRatingByHousingIds(List<UUID> housingIds);

    List<Review> findAllByAuthorId(UUID uuid);

    void deleteAllByAuthorId(UUID id);
}
