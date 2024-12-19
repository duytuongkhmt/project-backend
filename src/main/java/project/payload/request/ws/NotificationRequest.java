package project.payload.request.ws;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private String userId;
    private String followerName;
}
