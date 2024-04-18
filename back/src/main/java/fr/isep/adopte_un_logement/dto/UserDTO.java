package fr.isep.adopte_un_logement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String firstName;

    private String lastName;

    private String email;
    private List<String> roles;

}
