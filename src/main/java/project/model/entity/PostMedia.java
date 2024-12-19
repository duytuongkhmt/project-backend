package project.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="post_medias")
public class PostMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
//    @Column(name = "post_id")
//    private String postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String url;

    private String type; // image, video, etc.

    @Column(name="`order`")
    private Integer order;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static class TYPE {
        public static final String IMAGE = "image";
        public static final String VIDEO = "video";
    }
}
