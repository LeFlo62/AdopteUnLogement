package fr.isep.adopte_un_logement;

import fr.isep.adopte_un_logement.config.ApplicationConfig;
import fr.isep.adopte_un_logement.entities.Role;
import fr.isep.adopte_un_logement.entities.User;
import fr.isep.adopte_un_logement.model.ERole;
import fr.isep.adopte_un_logement.repositories.RoleRepository;
import fr.isep.adopte_un_logement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final RoleRepository roleRepository;
    private final ApplicationConfig applicationConfig;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;

        for(ERole role : ERole.values()){
            createRoleIfNotFound(role);
        }

        if(!userService.existsByEmail(applicationConfig.getApiCreationUserUsername())){
            User user = new User();
            user.setEmail(applicationConfig.getApiCreationUserUsername());
            user.setPassword(passwordEncoder.encode(applicationConfig.getApiCreationUserPassword()));
            user = userService.createUser(user);
            user.setHousings(List.of());
            user.setFirstName(applicationConfig.getApiCreationUserUsername());
            user.setLastName(applicationConfig.getApiCreationUserUsername());
            user.setRoles(List.of(roleRepository.findByName(ERole.ADMIN).get()));
            userService.updateUser(user);
        }

        alreadySetup = true;
    }

    private void createRoleIfNotFound(ERole name){
        if(roleRepository.findByName(name).isEmpty()){
            Role r = new Role();
            r.setName(name);
            roleRepository.save(r);
        }
    }

}
