package project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @UuidGenerator
    private String id;
    private String artistId;
    private String content;
    private Double time;
    private String title;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
