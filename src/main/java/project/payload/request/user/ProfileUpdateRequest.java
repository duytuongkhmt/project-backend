package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProfileUpdateRequest {
    private String id;
    private String fullName;
    private String mobile;
    private String gender;
    private LocalDate birthday;
    private Integer yearOfExperience;
    private Integer bookNumber;
    private Double hourOfPerformance;
    private String address;
    private Double price;
    private String note;
    private String role;
    private String nickname;
    private String bio;
    private List<String> genre;
    private String profileCode;
}
