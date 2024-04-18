package fr.isep.adopte_un_logement.controller;

import fr.isep.adopte_un_logement.dto.MessageDTO;
import fr.isep.adopte_un_logement.dto.UserDTO;
import fr.isep.adopte_un_logement.mapper.MessageMapper;
import fr.isep.adopte_un_logement.mapper.UserMapper;
import fr.isep.adopte_un_logement.model.UserDetailsImpl;
import fr.isep.adopte_un_logement.service.MessageService;
import fr.isep.adopte_un_logement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    private MessageMapper messageMapper;
    private MessageService messageService;
    private UserService userService;
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("id") String otherId, Pageable pageable){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getId().toString();
        if(userId.equals(otherId) || !userService.userExists(UUID.fromString(otherId))){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messageMapper.toDTO(messageService.getMessages(userId, otherId, pageable).toList()));
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable("id") String otherId, @RequestBody String message){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getId().toString();

        if(userId.equals(otherId) || !userService.userExists(UUID.fromString(otherId))){
            return ResponseEntity.notFound().build();
        }

        MessageDTO messageDTO = MessageDTO.builder().senderId(userId).receiverId(otherId).content(message)
                .sendTime(System.currentTimeMillis()).build();
        return ResponseEntity.ok(messageMapper.toDTO(messageService.addMessage(messageMapper.toEntity(messageDTO))));
    }

    @GetMapping("lastUsers")
    public ResponseEntity<List<UserDTO>> getLastUsers(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getId().toString();
        return ResponseEntity.ok(userMapper.toDTO(messageService.getLastUsers(userId)));
    }

}
