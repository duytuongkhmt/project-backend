package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import project.model.entity.Conversation;
import project.payload.request.chat.ConversationCreateRequest;
import project.resource.chat.ConversationResource;
import project.service.ws.ConversationService;

@Component
@RequiredArgsConstructor
public class ConversationBusiness {
    private final ConversationService conversationService;

    public ConversationResource createConversation(ConversationCreateRequest request) {
        Conversation conversation = conversationService.createConversation(request);
        ConversationResource conversationResource = new ConversationResource();
        BeanUtils.copyProperties(conversation, conversationResource);
        return conversationResource;
    }

    public ConversationResource getConversationById(String id) {

    }
}
