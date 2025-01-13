package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.ReviewBusiness;
import project.payload.request.user.ReviewCreateRequest;
import project.payload.request.user.ReviewUpdateRequest;
import project.payload.response.ResponseObject;
import project.resource.ReviewResource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewBusiness reviewBusiness;

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> getReviewByArtist(@PathVariable String id) {
        List<ReviewResource> resources = reviewBusiness.getReviewByArtistId(id);
        return ResponseEntity.ok(new ResponseObject(resources));
    }

    @GetMapping({"/order/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> getReviewByOrder(@PathVariable String id) {
        ReviewResource resources = reviewBusiness.getReviewByOrder(id);
        return ResponseEntity.ok(new ResponseObject(resources));
    }

    @PostMapping({"/create", "/create/"})
    public ResponseEntity<ResponseObject> store(@RequestBody ReviewCreateRequest request) {
        reviewBusiness.saveReview(request);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }

    @PutMapping({"/update", "/update/"})
    public ResponseEntity<ResponseObject> update(@RequestBody ReviewUpdateRequest request) {
        reviewBusiness.updateReview(request);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }

    @DeleteMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> delete(@PathVariable String id) {
        reviewBusiness.deleteReview(id);
        return ResponseEntity.ok(new ResponseObject("Ok"));
    }
}
