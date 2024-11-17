package project.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Profile {
    @Id
    @UuidGenerator
    private String id;

    private String coverPhoto;
    private String userId;

    private String bio;

    private String stageName;
    private List<String> genre;
    private Double rate;


    private Double price;
    private String note;
    private String status;



}
