package project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @UuidGenerator
    private String id;
    @Column(name="artist_id")
    private String artistId;
    @Column(name="booker_id")
    private String bookerId;
    private LocalDateTime from;
    private LocalDateTime to;
    private Double price;
    private String note;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", insertable = false, updatable = false)
    @JsonBackReference
    private Account artist;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id", insertable = false, updatable = false)
//    @JsonBackReference
    private Account booker;

    public static class STATUS {
        public static final String INIT = "init";
        public static final String CONFIRM = "confirm";
        public static final String SUCCESS = "success";
        public static final String DELETE = "delete";
    }
}
