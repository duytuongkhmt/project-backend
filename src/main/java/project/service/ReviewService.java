package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.entity.Profile;
import project.model.entity.Review;
import project.repository.ProfileRepository;
import project.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProfileRepository profileRepository;

    public Review getById(String id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> getByArtist(String id) {
        return reviewRepository.findByArtistId(id);
    }

    public Review getByOrder (String id) {
        return reviewRepository.findByOrderId(id);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public String delete(String id) {
        Review review = getById(id);
        String artistId = review.getArtistId();
        reviewRepository.delete(review);
        return artistId;
    }

    public Double getRatingByArtist(String id) {
        List<Review> reviews = getByArtist(id);
        return calculateAverageRate(reviews);
    }

    private Double calculateAverageRate(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int totalRate = 0;

        for (Review review : reviews) {
            if (review != null && review.getRate() != null) {
                sum += review.getRate();
                totalRate++;
            }
        }

        return totalRate > 0 ? sum / totalRate : 0.0;
    }
}
