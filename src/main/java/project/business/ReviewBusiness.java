package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.service.ReviewService;

@Component
@RequiredArgsConstructor
public class ReviewBusiness {
    private final ReviewService reviewService;
}
