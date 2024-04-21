package project.resource;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UsersResource {
    private String username;
    private String password;
    private String fullName;
    private String mobile;
    private String email;
    private String rememberToken;
    private LocalDate emailVerifiedAt;
    private LocalDate confirmedAt;
    private String activeToken;
    private String verifyToken;
    private String avatar;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private Integer role;
    private Boolean isArtist;
    private String bio;
}
