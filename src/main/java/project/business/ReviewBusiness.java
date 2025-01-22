package project.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import project.model.entity.Account;
import project.model.entity.Profile;
import project.model.entity.Review;
import project.payload.request.user.ReviewCreateRequest;
import project.payload.request.user.ReviewUpdateRequest;
import project.resource.ProfileResource;
import project.resource.ReviewResource;
import project.service.ProfileService;
import project.service.ReviewService;
import project.service.UserService;
import project.util.AuthUtils;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewBusiness {
    private final ReviewService reviewService;
    private final ProfileService profileService;
    private final UserService userService;

    public List<ReviewResource> getReviewByArtistId(String id) {
        List<Review> reviews = reviewService.getByArtist(id);
        return reviews.stream().map(review -> {
            ReviewResource reviewResource = new ReviewResource();
            ProfileResource profileResource = new ProfileResource();
            BeanUtils.copyProperties(review, reviewResource);
            BeanUtils.copyProperties(review.getUser(), profileResource);
            reviewResource.setUser(profileResource);
            return reviewResource;
        }).toList();
    }

    public ReviewResource getReviewByOrder(String id) {
        Review review = reviewService.getByOrder(id);
        if (review == null) {
            return null;
        }
        ReviewResource reviewResource = new ReviewResource();
        ProfileResource profileResource = new ProfileResource();
        BeanUtils.copyProperties(review, reviewResource);
        BeanUtils.copyProperties(review.getUser(), profileResource);
        reviewResource.setUser(profileResource);
        return reviewResource;
    }

    @Transactional
    public void saveReview(ReviewCreateRequest request) {

        String profileId = AuthUtils.getCurrentProfileId();
        Review review = new Review();
        BeanUtils.copyProperties(request, review);
        review.setUserId(profileId);
        reviewService.save(review);
        updateRateProfile(request.getArtistId());
    }

    @Transactional
    public void updateReview(ReviewUpdateRequest request) {
        Review review = reviewService.getById(request.getId());
        BeanUtils.copyProperties(request, review);
        reviewService.save(review);
        updateRateProfile(request.getArtistId());
    }

    @Transactional
    public void deleteReview(String id) {
        String artistId = reviewService.delete(id);
        updateRateProfile(artistId);
    }

    public ReviewResource getReview(String id) {
        ReviewResource reviewResource = new ReviewResource();
        Review review = reviewService.getById(id);
        BeanUtils.copyProperties(review, reviewResource);
        return reviewResource;
    }

    private void updateRateProfile(String id) {
        Profile profile = profileService.getProfileById(id);
        Double newRate = reviewService.getRatingByArtist(id);
        profile.setRate(newRate);
    }


}
