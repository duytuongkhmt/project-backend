package project.resource.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MessageResource {
    private String id;
    private String sender;
    private String content;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
