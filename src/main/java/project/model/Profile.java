package project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import io.hypersistence.utils.hibernate.type.json.JsonType;

import java.time.LocalDate;
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
    private String avatar;
    private String coverPhoto;
    private String bio;
    private String nickname;
    @Type(JsonType.class)
    @Column( columnDefinition = "JSONB")
    private List<String> genre;
    private Double rate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;
    private LocalDate birthday;
    private Integer yearOfExperience;
    private Integer bookNumber;
    private Double hourOfPerformance;
    private String address;
    private Double price;
    private String note;
    private String status;

}
