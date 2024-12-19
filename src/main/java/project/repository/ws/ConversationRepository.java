package project.repository.ws;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Conversation;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, String> {

}
