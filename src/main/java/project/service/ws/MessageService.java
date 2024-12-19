package project.service.ws;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.entity.Message;
import project.repository.ws.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    // Lấy lịch sử tin nhắn của một cuộc trò chuyện
    public List<Message> getMessages(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
}
