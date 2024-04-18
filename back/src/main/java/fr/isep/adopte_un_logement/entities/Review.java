package fr.isep.adopte_un_logement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Housing housing;

    @Lob
    private String content;

    private float rating = 0;

    private long time;

}
