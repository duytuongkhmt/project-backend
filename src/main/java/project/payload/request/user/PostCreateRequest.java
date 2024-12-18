package project.payload.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostCreateRequest {
    @NotNull(message = "Title is required")
    private String title;

    private String content;

    @NotNull(message = "Media cannot be empty")
    private List<MediaRequest> mediaList;

    private List<String> tags;

    @NotNull(message = "User ID is required")
    private String userId;

    @Data
    @NotNull
    public static class MediaRequest {
        @NotBlank(message = "Media URL is required")
        private String url;

        @NotBlank(message = "Media type is required")
        private String type; // image, video, etc.

        @NotNull(message = "Media order is required")
        private Integer order;
    }
}
