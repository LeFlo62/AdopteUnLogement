package fr.isep.adopte_un_logement.repositories;

import fr.isep.adopte_un_logement.entities.Role;
import fr.isep.adopte_un_logement.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
