package project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "artist_id")
    private String artistId;
    @Column(name = "booker_id")
    private String bookerId;
    @Column(name = "`from`")
    private LocalDateTime from;
    @Column(name = "`to`")
    private LocalDateTime to;
    private Double price;
    private String note;
    private String address;
    private String status = STATUS.PENDING;
    private String reasonReject;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", insertable = false, updatable = false)
    @JsonBackReference
    private Profile artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id", insertable = false, updatable = false)
    @JsonBackReference
    private Profile booker;

    public static class STATUS {
        public static final String PENDING = "pending";
        public static final String CONFIRMED = "confirmed";
        public static final String SUCCESS = "success";
        public static final String DELETED = "deleted";
        public static final String CANCEL = "cancel";

    }
}
