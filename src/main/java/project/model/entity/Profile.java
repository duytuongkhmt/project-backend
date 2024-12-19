package project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String gender;
    private String userId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false , insertable = false, updatable = false)
    @JsonBackReference
    private Account user;
    private LocalDate birthday;
    private Integer yearOfExperience;
    private Integer bookNumber;
    private Double hourOfPerformance;
    private String address;
    private Double price;
    private String note;
    private String status;
    private String profileCode;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private List<Order> artistOrders;

    @OneToMany(mappedBy = "booker", fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private List<Order> userOrders;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private Bank bank;

}
