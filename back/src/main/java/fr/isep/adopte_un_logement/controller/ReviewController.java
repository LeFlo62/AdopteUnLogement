package fr.isep.adopte_un_logement.controller;

import fr.isep.adopte_un_logement.dto.ReviewAverageDTO;
import fr.isep.adopte_un_logement.dto.ReviewCreationDTO;
import fr.isep.adopte_un_logement.dto.ReviewDTO;
import fr.isep.adopte_un_logement.entities.Review;
import fr.isep.adopte_un_logement.mapper.ReviewMapper;
import fr.isep.adopte_un_logement.model.ReviewAverage;
import fr.isep.adopte_un_logement.model.UserDetailsImpl;
import fr.isep.adopte_un_logement.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/review")
@RestController
@AllArgsConstructor
public class ReviewController {

    private ReviewService reviewService;
    private ReviewMapper reviewMapper;


    @GetMapping("/list/{housingId}")
    public ResponseEntity<List<ReviewDTO>> getReviewListByHousingId(@PathVariable("housingId") String housingId, Pageable pageable) {
        return ResponseEntity.ok(reviewMapper.toDTO(reviewService.getReviewListByHousingId(housingId, pageable)));
    }

    @GetMapping("/listByUser/{housingId}")
    public ResponseEntity<List<ReviewDTO>> getReviewListByUserId(@PathVariable("housingId") String housingId) {
        return ResponseEntity.ok(reviewMapper.toDTO(reviewService.getReviewListByUserId(housingId)));
    }

    @PostMapping("/create")
    public HttpStatus createReview(@RequestBody ReviewCreationDTO reviewDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getId().toString();

        reviewDTO.setAuthorId(userId);

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setTime(System.currentTimeMillis());

        reviewService.createReview(review);

        return HttpStatus.OK;
    }

    @GetMapping("/average/{housingId}")
    public ResponseEntity<ReviewAverageDTO> getAverageRatingByHousingId(@PathVariable("housingId") String housingId) {
        return ResponseEntity.ok(reviewMapper.toDTO(new ReviewAverage(housingId, reviewService.getAverageRatingByHousingId(housingId))));
    }

    @PostMapping("/averageMultiple")
    public ResponseEntity<List<ReviewAverageDTO>> getAverageRatingByHousingId(@RequestBody List<String> housingIds) {
        return ResponseEntity.ok(reviewMapper.toDTOAverageList(reviewService.getAverageRatingByHousingIds(housingIds)));
    }

}
