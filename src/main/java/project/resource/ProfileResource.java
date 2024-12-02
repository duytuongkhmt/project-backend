package project.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileResource {
    private String id;
    private String fullName;
    private String nickname;
    private String mobile;
    private String email;
    private String coverPhoto;
    private String avatar;
    private String role;
    private LocalDate birthday;
    private Integer yearOfExperience;
    private Integer bookNumber;
    private Double hourOfPerformance;
    private String address;
    private Double price;
    private String note;
}
