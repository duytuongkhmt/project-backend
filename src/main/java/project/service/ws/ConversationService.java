package project.service.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import project.model.entity.Account;
import project.model.entity.Conversation;
import project.model.entity.Message;
import project.payload.request.chat.ConversationCreateRequest;
import project.repository.UserRepository;
import project.repository.ws.ConversationRepository;
import project.repository.ws.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Conversation createConversation(ConversationCreateRequest request) {
        Conversation conversation=new Conversation();
        BeanUtils.copyProperties(request,conversation);
        return conversationRepository.save(conversation);
    }

    public Conversation getConversation(String id) {
        return conversationRepository.findById(id).orElse(null);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

//    public Conversation getOrCreateConversation(String userId, String conversationId) {
//        if(conversationId==null) {
//            Conversation conversation=new Conversation();
//            conversation.set
//            return conversation.
//        }
//
//            Conversation conversation= conversationRepository.findById(conversationId).orElse(null);
//            if (conversation == null) {
//
//            }
//        }
//        // Kiểm tra xem cuộc trò chuyện đã tồn tại chưa
//        return conversationRepository
//                .findByUserAndName(user, conversationName)
//                .orElseGet(() -> createConversation(user, conversationName));
//    }
//
//    private Conversation createConversation(User user, String name) {
//        Conversation conversation = new Conversation();
//        conversation.setName(name);
//        conversation.setUser(user);
//        return conversationRepository.save(conversation);
//    }
//
//    public Message saveMessage(Conversation conversation, Account user, String content) {
//        Message message = new Message();
//        message.setConversation(conversation);
//        message.setSenderId(user.getId());
//        message.setContent(content);
//        return messageRepository.save(message);
//    }
}
