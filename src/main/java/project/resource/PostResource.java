package project.resource;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResource {
    private String id;
    private String title;
    private String content;
    private List<MediaResponse> media;
    private List<String> tags;
    private String authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Data
    @Builder
    public static class MediaResponse {
        private String url;
        private String type;
        private Integer order;
    }
}
