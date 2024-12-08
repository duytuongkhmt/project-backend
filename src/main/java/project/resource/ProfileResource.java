package project.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import project.common.Constant;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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
    private String bio;
    private String gender;
    private String totalFollowers;
    private String totalFriends;
    private String profileCode;
    private List<String> genre;

    public Integer getAge(){
        if (birthday == null) {
            return null;
        }
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
