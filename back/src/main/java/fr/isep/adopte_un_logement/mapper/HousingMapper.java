package fr.isep.adopte_un_logement.mapper;

import fr.isep.adopte_un_logement.config.ApplicationConfig;
import fr.isep.adopte_un_logement.dto.HousingCreationDTO;
import fr.isep.adopte_un_logement.dto.HousingDTO;
import fr.isep.adopte_un_logement.dto.HousingListItemDTO;
import fr.isep.adopte_un_logement.entities.Housing;
import fr.isep.adopte_un_logement.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class HousingMapper {

    private final ApplicationConfig applicationConfig;

    @Autowired
    public HousingMapper(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Transactional(readOnly = true)
    public HousingListItemDTO toDTO(Housing housing) {
        return HousingListItemDTO.builder()
                .id(housing.getId().toString())
                .title(housing.getTitle())
                .authorName(housing.getAuthor().getFirstName() + " " + housing.getAuthor().getLastName())
                .image(applicationConfig.getApiUrl() + "image/" + housing.getImages().get(0))
                .build();
    }

    public List<HousingListItemDTO> toDTO(List<Housing> entities){
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Housing toEntity(HousingCreationDTO housingCreationDTO) {
        return Housing.builder()
                .title(housingCreationDTO.getTitle())
                .description(housingCreationDTO.getDescription())
                .author(User.builder().id(UUID.fromString(housingCreationDTO.getAuthorId())).build())
                .services(housingCreationDTO.getServices())
                .constraints(housingCreationDTO.getConstraints())
                .build();
    }

    public Housing toEntity(HousingDTO housingDTO){
        return Housing.builder()
                .id(UUID.fromString(housingDTO.getId()))
                .title(housingDTO.getTitle())
                .description(housingDTO.getDescription())
                .author(User.builder().id(UUID.fromString(housingDTO.getAuthorId())).build())
                .services(housingDTO.getServices())
                .constraints(housingDTO.getConstraints())
                .build();
    }

    public HousingDTO toHousingDTO(Housing housing) {
        return HousingDTO.builder()
                .id(housing.getId().toString())
                .title(housing.getTitle())
                .authorId(housing.getAuthor().getId().toString())
                .authorName(housing.getAuthor().getFirstName() + " " + housing.getAuthor().getLastName())
                .description(housing.getDescription())
                .services(housing.getServices())
                .constraints(housing.getConstraints())
                .images(housing.getImages().stream().map(id -> applicationConfig.getApiUrl() + "image/" + id).collect(Collectors.toList()))
                .build();
    }

    public List<HousingDTO> toHousingDTO(List<Housing> entities){
        return entities.stream().map(this::toHousingDTO).collect(Collectors.toList());
    }

    public void updateEntity(HousingDTO update, Housing housing) {
        if(update.getTitle() != null) housing.setTitle(update.getTitle());
        if(update.getDescription() != null) housing.setDescription(update.getDescription());
        if(update.getServices() != null) housing.setServices(update.getServices());
        if(update.getConstraints() != null) housing.setConstraints(update.getConstraints());
    }
}
