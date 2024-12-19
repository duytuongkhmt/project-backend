package project.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResource {
    private String id;
    private String comment;
    private Double rate;
    private String orderId;
    private String userId;
    private String artistId;
    private ProfileResource user;
    private LocalDateTime updatedAt;
}
