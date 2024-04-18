package fr.isep.adopte_un_logement.mapper;

import fr.isep.adopte_un_logement.dto.ReviewAverageDTO;
import fr.isep.adopte_un_logement.dto.ReviewCreationDTO;
import fr.isep.adopte_un_logement.dto.ReviewDTO;
import fr.isep.adopte_un_logement.dto.ReviewUpdateDTO;
import fr.isep.adopte_un_logement.entities.Housing;
import fr.isep.adopte_un_logement.entities.Review;
import fr.isep.adopte_un_logement.entities.User;
import fr.isep.adopte_un_logement.model.ReviewAverage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(Review review){
        return ReviewDTO.builder()
                .id(review.getId().toString())
                .authorId(review.getAuthor().getId().toString())
                .authorName(review.getAuthor().getFirstName() + " " + review.getAuthor().getLastName())
                .housingId(review.getHousing().getId().toString())
                .rating(review.getRating())
                .content(review.getContent())
                .time(review.getTime())
                .build();
    }

    public List<ReviewDTO> toDTO(List<Review> reviewListByHousingId) {
        return reviewListByHousingId.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Review toEntity(ReviewCreationDTO reviewDTO) {
        return Review.builder()
                .author(User.builder().id(UUID.fromString(reviewDTO.getAuthorId())).build())
                .housing(Housing.builder().id(UUID.fromString(reviewDTO.getHousingId())).build())
                .rating(reviewDTO.getRating())
                .content(reviewDTO.getContent())
                .build();
    }

    public ReviewAverageDTO toDTO(ReviewAverage reviewAverage) {
        return ReviewAverageDTO.builder()
                .housingId(reviewAverage.getHousingId().toString())
                .rating(reviewAverage.getRating())
                .build();
    }

    public List<ReviewAverageDTO> toDTOAverageList(List<ReviewAverage> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void updateEntity(ReviewUpdateDTO update, Review review) {
        if(update.getId() != null) review.setId(UUID.fromString(update.getId()));
        if(update.getContent() != null) review.setContent(update.getContent());
        if(update.getRating() != -1) review.setRating(update.getRating());
    }
}
