package project.payload.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String currentPassword;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String confirmPassword;
}
