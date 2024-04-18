package fr.isep.adopte_un_logement.controller;

import fr.isep.adopte_un_logement.dto.*;
import fr.isep.adopte_un_logement.entities.Housing;
import fr.isep.adopte_un_logement.entities.Review;
import fr.isep.adopte_un_logement.entities.Role;
import fr.isep.adopte_un_logement.entities.User;
import fr.isep.adopte_un_logement.mapper.HousingMapper;
import fr.isep.adopte_un_logement.mapper.ReviewMapper;
import fr.isep.adopte_un_logement.mapper.UserMapper;
import fr.isep.adopte_un_logement.model.ERole;
import fr.isep.adopte_un_logement.service.HousingService;
import fr.isep.adopte_un_logement.service.ReviewService;
import fr.isep.adopte_un_logement.service.RoleService;
import fr.isep.adopte_un_logement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class AdminController {

    private UserMapper userMapper;
    private UserService userService;

    private RoleService roleService;

    private HousingService housingService;
    private HousingMapper housingMapper;

    private ReviewService reviewService;
    private ReviewMapper reviewMapper;

    private PasswordEncoder encoder;

    @GetMapping("/user/count")
    public ResponseEntity<Long> getUserCount(){
        return ResponseEntity.ok(this.userService.getUsersCount());
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable){
        return ResponseEntity.ok(this.userMapper.toDTO(this.userService.getUsers(pageable)));
    }

    @PostMapping("/user/update")
    public HttpStatus updateUser(@RequestBody UserUpdateDTO update){
        if(update.getPassword() != null){
            update.setPassword(encoder.encode(update.getPassword()));
        }

        Optional<User> userOpt = this.userService.getUser(update.getId());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            this.userMapper.updateEntity(update, user);

            List<Role> roles = update.getRoles().stream().map(ERole::valueOf).map(roleService::findByName).collect(Collectors.toList());

            user.setRoles(roles);

            this.userService.updateUser(user);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @PostMapping("/user/delete")
    public HttpStatus deleteUser(@RequestBody String userId){
        Optional<User> userOpt = this.userService.getUser(userId);
        if(userOpt.isPresent()){
            this.userService.deleteUser(userOpt.get());
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping("/housing/count")
    public ResponseEntity<Long> getHousingCount(){
        return ResponseEntity.ok(this.housingService.getHousingsCount());
    }

    @GetMapping("/housing/list")
    public ResponseEntity<List<HousingDTO>> getHousings(Pageable pageable){
        return ResponseEntity.ok(this.housingMapper.toHousingDTO(this.housingService.getHousingListPaginated(pageable).getContent()));
    }

    @PostMapping("/housing/update")
    public HttpStatus updateHousing(@RequestBody HousingDTO update){
        Optional<Housing> housingOpt = this.housingService.getHousingById(UUID.fromString(update.getId()));
        if(housingOpt.isPresent()){
            Housing housing = housingOpt.get();
            this.housingMapper.updateEntity(update, housing);

            this.housingService.updateHousing(housing);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @PostMapping("/housing/delete")
    public HttpStatus deleteHousing(@RequestBody String housingId){
        Optional<Housing> housingOpt = this.housingService.getHousingById(UUID.fromString(housingId));
        if(housingOpt.isPresent()){
            this.housingService.deleteHousing(UUID.fromString(housingId));
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping("/review/count")
    public ResponseEntity<Long> getReviewCount(){
        return ResponseEntity.ok(this.reviewService.getReviewsCount());
    }

    @GetMapping("/review/list")
    public ResponseEntity<List<ReviewDTO>> getReviews(Pageable pageable){
        return ResponseEntity.ok(this.reviewMapper.toDTO(this.reviewService.getReviews(pageable)));
    }

    @PostMapping("/review/update")
    public HttpStatus updateReview(@RequestBody ReviewUpdateDTO update){
        Optional<Review> reviewOpt = this.reviewService.getReviewById(UUID.fromString(update.getId()));
        if(reviewOpt.isPresent()){
            Review review = reviewOpt.get();
            this.reviewMapper.updateEntity(update, review);

            this.reviewService.updateReview(review);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @PostMapping("/review/delete")
    public HttpStatus deleteReview(@RequestBody String reviewId){
        Optional<Review> reviewOpt = this.reviewService.getReviewById(UUID.fromString(reviewId));
        if(reviewOpt.isPresent()){
            this.reviewService.deleteReview(UUID.fromString(reviewId));
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

}
