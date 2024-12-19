package project.repository.ws;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, String> {

}
