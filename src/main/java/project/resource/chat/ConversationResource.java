package project.resource.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class ConversationResource {
    private String id;
    private String name;
    private List<MessageResource> messages;

}
