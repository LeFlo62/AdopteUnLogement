package fr.isep.adopte_un_logement.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor
@ToString
public class UserUpdateDTO {

    private String id;
    private String firstName;

    private String lastName;

    private String email;
    private String password;

    private List<String> roles;

}
