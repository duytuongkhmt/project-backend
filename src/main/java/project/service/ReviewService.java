package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.entity.Review;
import project.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review getById(String id) {
        return reviewRepository.findById(id).orElse(null);
    }
    public List<Review> getByArtist(String id){
        return reviewRepository.findByArtistId(id);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void delete(String id) {
        Review review = getById(id);
        if (review != null) {
            reviewRepository.delete(review);
        }
    }
}
