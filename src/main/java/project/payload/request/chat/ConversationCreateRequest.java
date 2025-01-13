package project.payload.request.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConversationCreateRequest {
    private String name;
    private String user1;
    private String user2;
}
