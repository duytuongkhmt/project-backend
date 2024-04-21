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
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @UuidGenerator
    private String id;
    private String artistId;
    private String content;
    private String picture;
    private Double rating;
    private List<String> followes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
