package fr.isep.adopte_un_logement.dto;

import fr.isep.adopte_un_logement.entities.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class MessageDTO {

    private UUID id;

    private String senderId;
    private String receiverId;
    private String senderName;
    private String receiverName;

    private String content;

    private long sendTime;

}
