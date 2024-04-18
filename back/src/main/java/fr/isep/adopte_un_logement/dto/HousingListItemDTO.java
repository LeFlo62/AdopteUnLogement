package fr.isep.adopte_un_logement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class HousingListItemDTO {

    private String id;
    private String title;
    private String authorName;
    private String image;

}
