package project.resource;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResource {
    private String id;
    private String title;
    private String content;
    private List<MediaResource> medias;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProfileResource profile;
    private List<ProfileResource> likePeople;
    private List<CommentResource> comments;

}
