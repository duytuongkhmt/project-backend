package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {
    private String id;
    private String comment;
    private Double rate;
    private String orderId;
    private String userId;
    private String artistId;
}
