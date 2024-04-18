package fr.isep.adopte_un_logement.mapper;

import fr.isep.adopte_un_logement.dto.UserCreationDTO;
import fr.isep.adopte_un_logement.dto.UserDTO;
import fr.isep.adopte_un_logement.dto.UserUpdateDTO;
import fr.isep.adopte_un_logement.entities.Role;
import fr.isep.adopte_un_logement.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Role toEntity(String value);

    List<Role> toEntity(List<String> value);

    default String toDTORole(Role value) {
        return value.getName().toString();
    };

    List<String> toDTORoles(List<Role> value);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UserUpdateDTO dto, @MappingTarget User user);

    User toEntity(UserCreationDTO userDTO);
    UserDTO toDTO(User user);
    List<UserDTO> toDTO(List<User> entities);

    User toEntity(UserUpdateDTO userDTO);

}
