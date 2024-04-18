package fr.isep.adopte_un_logement.model;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@ToString
public class ReviewAverage {

    private String housingId;
    private float rating;

}
