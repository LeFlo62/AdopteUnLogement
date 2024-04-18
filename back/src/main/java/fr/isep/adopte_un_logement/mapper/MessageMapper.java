package fr.isep.adopte_un_logement.mapper;

import fr.isep.adopte_un_logement.dto.MessageDTO;
import fr.isep.adopte_un_logement.entities.Message;
import fr.isep.adopte_un_logement.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MessageMapper {


    public Message toEntity(MessageDTO messageDTO) {
        return Message.builder()
                .id(messageDTO.getId())
                .receiver(User.builder().id(UUID.fromString(messageDTO.getReceiverId())).build())
                .sender(User.builder().id(UUID.fromString(messageDTO.getSenderId())).build())
                .content(messageDTO.getContent())
                .sendTime(messageDTO.getSendTime())
                .build();
    }

    public MessageDTO toDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .receiverId(message.getReceiver().getId().toString())
                .receiverName(message.getReceiver().getFirstName() + " " + message.getReceiver().getLastName())
                .senderId(message.getSender().getId().toString())
                .senderName(message.getSender().getFirstName() + " " + message.getSender().getLastName())
                .content(message.getContent())
                .sendTime(message.getSendTime())
                .build();
    }

    public List<MessageDTO> toDTO(List<Message> entities){
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
