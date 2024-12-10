package project.payload.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostUpdateRequest {
    private String id;
    private String title;

    private String content;

    private List<MediaRequest> mediaList;

    private List<String> tags;

    private String userId;

    @Data
    public static class MediaRequest {
        private String url;

        private String type; // image, video, etc.

        private Integer order;
    }
}
