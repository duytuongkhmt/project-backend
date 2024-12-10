package project.service.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Conversation;
import project.repository.ws.ConversationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    public Conversation getConversation(String id) {
        return conversationRepository.findById(id).orElse(null);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
}
