package project.controller.ws;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.model.entity.Message;
import project.service.ws.MessageService;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;

    // Nhận tin nhắn và gửi đến tất cả các thành viên trong cuộc trò chuyện
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {
        // Lưu tin nhắn vào cơ sở dữ liệu
        messageService.sendMessage(message);

        // Gửi tin nhắn đến tất cả người dùng trong cuộc trò chuyện
        messagingTemplate.convertAndSend("/topic/messages/" + message.getConversation().getId(), message);
    }

    // API này để client kết nối đến và nhận tin nhắn theo ID cuộc trò chuyện
    @MessageMapping("/chat.addUser")
    public void addUser(String username) {
        messagingTemplate.convertAndSend("/topic/users", username);
    }
}
