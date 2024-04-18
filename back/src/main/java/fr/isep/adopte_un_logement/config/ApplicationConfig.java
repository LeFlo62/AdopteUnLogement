package fr.isep.adopte_un_logement.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.creation.user.username}")
    private String apiCreationUserUsername;

    @Value("${api.creation.user.password}")
    private String apiCreationUserPassword;

}
