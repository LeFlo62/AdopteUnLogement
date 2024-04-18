package fr.isep.adopte_un_logement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDTO {

    private String email;
    private String password;

}
