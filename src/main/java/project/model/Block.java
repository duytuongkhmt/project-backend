package project.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blocks")
public class Block {
    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    @JoinColumn(name = "blocker_id", nullable = false)
    private Account blocker; // Người chặn

    @ManyToOne
    @JoinColumn(name = "blocked_id", nullable = false)
    private Account blocked; // Người bị chặn

    @CreationTimestamp
    private LocalDateTime createdAt;
}
