package project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import io.hypersistence.utils.hibernate.type.json.JsonType;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @UuidGenerator
    private String id;

    private String coverPhoto;
//    @Column( name = "user_id")
//    private String userId;
    private String username;

    private String bio;

    private String stageName;
    @Type(JsonType.class)
    @Column( columnDefinition = "JSONB")
    private List<String> genre;
    private Double rate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    private Double price;
    private String note;
    private String status;

    public static class STATUS {
        public static final String SHOW = "show";
        public static final String DELETE = "delete";
    }
}
