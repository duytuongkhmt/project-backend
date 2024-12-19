package project.repository.ws;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    // Các phương thức truy vấn tùy chỉnh nếu cần
    List<Message> findByConversationId(String conversationId);
}
