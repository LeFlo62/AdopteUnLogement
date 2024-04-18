package fr.isep.adopte_un_logement.service;

import fr.isep.adopte_un_logement.entities.User;
import fr.isep.adopte_un_logement.model.UserDetailsImpl;
import fr.isep.adopte_un_logement.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private HousingService housingService;
    private ReviewService reviewService;
    private MessageService messageService;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public long getUsersCount() {
        return userRepository.count();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(UUID userId) {
        return userRepository.existsById(userId);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

        return UserDetailsImpl.build(user);
    }

    public Optional<User> getUser(String id) {
        return this.userRepository.findById(UUID.fromString(id));
    }

    public List<User> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable).getContent();
    }

    @Transactional
    public void deleteUser(User user) {
        user.getRoles().clear();
        housingService.deleteHousings(user.getHousings());
        reviewService.deleteReviewsFromUser(user.getId());
        user.getHousings().clear();
        messageService.deleteMessagesFromUser(user.getId());
        this.userRepository.delete(user);
    }
}
