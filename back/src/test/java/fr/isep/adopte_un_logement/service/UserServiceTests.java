package fr.isep.adopte_un_logement.service;

import fr.isep.adopte_un_logement.entities.User;
import fr.isep.adopte_un_logement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User user;

    @BeforeAll
    public static void init(){
        user = Mockito.mock(User.class);
        user.setFirstName("Jean");
        user.setLastName("DUPONT");
        user.setEmail("test@example.com");
    }


    /**
     * Tests {@link UserService#createUser(User)}
     */
    @Test
    public void createUserTest(){
        User userMock = Mockito.mock(User.class);
        userMock.setId(UUID.randomUUID());
        userMock.setFirstName(this.user.getFirstName());
        userMock.setLastName(this.user.getLastName());
        userMock.setEmail(this.user.getEmail());

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userMock);

        User userTest = userService.createUser(this.user);

        assertEquals(userMock, userTest);

        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
    public void updateUserTest(){
        User userMock = Mockito.mock(User.class);
        userMock.setId(UUID.randomUUID());
        userMock.setFirstName(this.user.getFirstName());
        userMock.setLastName(this.user.getLastName());
        userMock.setEmail(this.user.getEmail());

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userMock);

        User userTest = userService.updateUser(this.user);

        assertEquals(userMock, userTest);

        Mockito.verify(userRepository).save(Mockito.any());
    }

}