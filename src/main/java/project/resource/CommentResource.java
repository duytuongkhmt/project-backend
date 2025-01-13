package project.resource;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResource {
    private String id;
    private String content;
    private String postId;
    private LocalDateTime createdAt;
    private ProfileResource profile;
}
