package project.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
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
    private Integer totalBook;
    private Double hourOfPerformance;
    private String address;
    private String note;
    private String bio;
    private String gender;
    private Integer totalFollower;
    private Integer totalFollowing;
    private Integer totalFriend;
    private String profileCode;
    private Double rate;
    private List<String> genre;
    private BankResource bank;
    private LocalDate joinDay;

    public Integer getAge(){
        if (birthday == null) {
            return null;
        }
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
