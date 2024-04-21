package project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="post")
public class Post {
    @Id
    @UuidGenerator
    private String id;
    private String artistId;
    private String title;
    private String content;
    private String picture;
    private String video;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
