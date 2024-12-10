package project.resource;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UserResource {
    private String username;
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
    private String role;
    private String profileCode;
    private String profileId;
}
