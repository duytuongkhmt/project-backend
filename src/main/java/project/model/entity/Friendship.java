package project.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "friendships")
@Data
public class Friendship {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private Account receiver;


    @Column(nullable = false)
    private String status = STATUS.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public static class STATUS {
        public static final String PENDING = "pending";
        public static final String ACCEPTED = "accepted";
        public static final String BLOCKED = "blocked";
    }
}
