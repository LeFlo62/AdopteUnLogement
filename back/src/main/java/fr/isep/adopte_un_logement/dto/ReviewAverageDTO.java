package fr.isep.adopte_un_logement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ReviewAverageDTO {

    private String housingId;
    private float rating;

}
