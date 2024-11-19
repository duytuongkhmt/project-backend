package project.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "followers")
public class Follower {
    @Id
    @UuidGenerator
    private String id;

//    @Column(name = "user_id")
//    private String userId;
//    @Column(name = "follower_id")
//    private String followerId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Account follower;

    @CreationTimestamp
    private LocalDateTime createdAt ;
}
