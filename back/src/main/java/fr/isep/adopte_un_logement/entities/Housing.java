package fr.isep.adopte_un_logement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Table
@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Housing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    private UUID id;

    private String title;

    @ManyToOne
    private User author;

    private float rating = 0;

    @ElementCollection
    @JoinColumn(name = "housing_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})
    private List<UUID> images;

    @Lob
    private String description;

    @ElementCollection
    @JoinColumn(name = "housing_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})
    private List<String> services;

    @ElementCollection
    @JoinColumn(name = "housing_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})
    private List<String> constraints;
}
