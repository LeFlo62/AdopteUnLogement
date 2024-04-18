package fr.isep.adopte_un_logement.repositories;

import fr.isep.adopte_un_logement.entities.Housing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HousingRepository extends JpaRepository<Housing, UUID> {
    List<Housing> findAllByAuthorId(UUID uuid);
}
