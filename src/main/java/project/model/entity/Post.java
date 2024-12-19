package project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="posts")
public class Post {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "user_id")
    private String userId;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<PostMedia> mediaList;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp

    private LocalDateTime updatedAt;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Account user;

    public static class STATUS {
        public static final String SHOW = "show";
        public static final String DELETE = "delete";
    }
}
