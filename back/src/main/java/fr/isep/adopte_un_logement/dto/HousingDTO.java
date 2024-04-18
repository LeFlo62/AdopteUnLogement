package fr.isep.adopte_un_logement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class HousingDTO {
    private String id;
    private String authorId;
    private String authorName;
    private String title;
    private List<String> images;
    private List<String> services;
    private List<String> constraints;
    private String description;
    private String landlordDescription;

}
