package project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private List<Account> participants;

    @OneToMany(mappedBy = "conversation")
    @JsonManagedReference
    private List<Message> messages;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
