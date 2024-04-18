package fr.isep.adopte_un_logement.controller;

import fr.isep.adopte_un_logement.dto.UserDTO;
import fr.isep.adopte_un_logement.dto.UserUpdateDTO;
import fr.isep.adopte_un_logement.entities.User;
import fr.isep.adopte_un_logement.mapper.UserMapper;
import fr.isep.adopte_un_logement.model.ERole;
import fr.isep.adopte_un_logement.model.UserDetailsImpl;
import fr.isep.adopte_un_logement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/user")
@RestController
@AllArgsConstructor
public class UserController {

    private UserMapper userMapper;
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") String id){
        Optional<User> userOpt = this.userService.getUser(id);
        if(userOpt.isPresent()){
            return ResponseEntity.ok(this.userMapper.toDTO(userOpt.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO user){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getId().toString();
        System.out.println(user.getId());
        //Sets the update of the user to be theirs if they are not admin and the sent update isn't for them.
        if(!userId.equals(user.getId()) && userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(a -> ERole.ADMIN.toString().equalsIgnoreCase(a))){
            user.setId(userId);
        }

        Optional<User> savedOpt = userService.getUser(user.getId().toString());

        if(savedOpt.isPresent()){
            User saved = savedOpt.get();

            userMapper.updateEntity(user, saved);

            return ResponseEntity.ok(userMapper.toDTO(userService.updateUser(saved)));
        }
        return ResponseEntity.notFound().build();

    }

}
