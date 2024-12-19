package project.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.ProfileBusiness;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.ReviewCreateRequest;
import project.payload.response.ResponseObject;
import project.resource.OrderResource;
import project.resource.ReviewResource;
import project.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ProfileBusiness profileBusiness;
    private final ReviewService reviewService;

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> getReviewByArtist(@PathVariable String id) {
        List<ReviewResource> resources = profileBusiness.getReviews(id);
        return ResponseEntity.ok(new ResponseObject(resources));

    }

    @PostMapping({"/create", "/create/"})
    public ResponseEntity<ResponseObject> store(@RequestBody ReviewCreateRequest request) {

        ReviewResource reviewResource = reviewService.(request);
        return ResponseEntity.ok(ResponseObject.ok(orderResource));
    }
}
