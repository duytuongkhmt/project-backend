package project.controller.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.model.entity.Conversation;
import project.payload.request.chat.ConversationCreateRequest;
import project.service.ws.ConversationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;


    @PostMapping
    public Conversation createConversation(@RequestBody ConversationCreateRequest conversation) {
        return conversationService.createConversation(conversation);
    }

    @GetMapping("/{id}")
    public Conversation getConversation(@PathVariable String id) {
        return conversationService.getConversation(id);
    }

    @GetMapping
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversations();
    }
}
