package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.model.Conversation;

import java.util.List;
import java.util.stream.Collectors;

public class ConversationSpecification {
    public static Specification<Conversation> participantIn(List<String> participants) {
        return (root, cq, cb) -> {
            if (participants != null && !participants.isEmpty()) {

                String formattedArray = participants.stream()
                        .map(p -> "\"" + p + "\"")
                        .collect(Collectors.joining(",", "{", "}"));

                return cb.isTrue(cb.function("jsonb_exists_any", Boolean.class, root.get("id"), cb.literal(formattedArray)));
            } else {
                return cb.conjunction();
            }
        };
    }
}
