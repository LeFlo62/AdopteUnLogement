package fr.isep.adopte_un_logement.service;

import fr.isep.adopte_un_logement.entities.Role;
import fr.isep.adopte_un_logement.model.ERole;
import fr.isep.adopte_un_logement.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByName(ERole name) {
        return roleRepository.findByName(name).orElseThrow();
    }

}
