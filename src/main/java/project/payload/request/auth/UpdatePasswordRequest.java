package project.payload.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String oldPassword;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String confirmNewPassword;
}
