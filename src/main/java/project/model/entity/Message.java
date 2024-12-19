package project.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="massages")
public class Message {
    @Id
    @UuidGenerator
    private String id;
//    @Column(name = "conversation_id")
//    private String conversationId;
    private String senderId;

    @Enumerated(EnumType.STRING)
    private MessageType type; // TEXT, IMAGE, VIDEO


    public enum MessageType {
        TEXT, IMAGE, VIDEO
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
