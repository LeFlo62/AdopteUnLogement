package fr.isep.adopte_un_logement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter @Builder
public class HousingCreationDTO {

    private String title;

    private String authorId;

    private MultipartFile[] images;

    private String description;

    private List<String> services;
    private List<String> constraints;
}
