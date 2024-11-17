package project.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="conversations")
public class Conversation {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private Boolean isGroup;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> participants;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
