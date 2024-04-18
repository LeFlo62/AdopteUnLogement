package fr.isep.adopte_un_logement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ReviewUpdateDTO {

    private String id;

    private String content;

    private int rating = -1;

}
