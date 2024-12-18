package project.controller.ws;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.payload.request.ws.NotificationRequest;
import project.payload.response.ws.NotificationMessage;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/follower")
    public void notifyFollower(@RequestBody NotificationRequest request) {
        // Gửi thông báo tới người dùng được theo dõi
        messagingTemplate.convertAndSend(
                "/topic/follower/" + request.getUserId(),
                new NotificationMessage("Bạn có một follower mới!")
        );
    }
}
