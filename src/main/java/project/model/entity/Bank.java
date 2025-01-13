package project.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @UuidGenerator
    private String id;
    private String bankNumber;
    private String bankName;
    private String bankAddress;
    private String name;
    private String qr;
    @Column(name = "profile_id")
    private String profileId;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Profile profile;
}
