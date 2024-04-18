package fr.isep.adopte_un_logement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class StringDTO {

    private String value;

    private StringDTO(String value) {
        this.value = value;
    }

    public static StringDTO from(String value) {
        return new StringDTO(value);
    }

}
