package fr.isep.adopte_un_logement.service;

import fr.isep.adopte_un_logement.entities.Review;
import fr.isep.adopte_un_logement.model.ReviewAverage;
import fr.isep.adopte_un_logement.repositories.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewListByHousingId(String housingId, Pageable pageable) {
        return reviewRepository.findAllByHousingId(UUID.fromString(housingId), pageable).getContent();
    }

    public float getAverageRatingByHousingId(String housingId) {
        if(!reviewRepository.existsByHousingId(UUID.fromString(housingId))) return 0;
        return reviewRepository.getAverageRatingByHousingId(UUID.fromString(housingId));
    }

    public List<ReviewAverage> getAverageRatingByHousingIds(List<String> housingIds) {
        return reviewRepository.getAverageRatingByHousingIds(housingIds.stream().map(UUID::fromString).collect(Collectors.toList()))
                .stream().map(tuple -> new ReviewAverage(tuple.get(0, UUID.class).toString(), ((float)tuple.get(1, Double.class).doubleValue()))).collect(Collectors.toList());
    }

    public List<Review> getReviewListByUserId(String housingId) {
        return reviewRepository.findAllByAuthorId(UUID.fromString(housingId));
    }

    public void deleteReviewsFromUser(UUID id) {
        reviewRepository.deleteAllByAuthorId(id);
    }

    public long getReviewsCount() {
        return reviewRepository.count();
    }

    public List<Review> getReviews(Pageable pageable) {
        return this.reviewRepository.findAll(pageable).getContent();
    }

    public Optional<Review> getReviewById(UUID uuid) {
        return this.reviewRepository.findById(uuid);
    }

    public void updateReview(Review review) {
        this.reviewRepository.save(review);
    }

    public void deleteReview(UUID uuid) {
        this.reviewRepository.deleteById(uuid);
    }
}
