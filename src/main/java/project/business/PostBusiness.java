package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.service.PostService;

@Component
@RequiredArgsConstructor
public class PostBusiness {
    private final PostService postService;
}
