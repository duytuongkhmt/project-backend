package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateRequest {
    private String id;
    private String content;
    private String postId;
}
