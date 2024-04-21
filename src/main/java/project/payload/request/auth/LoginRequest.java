package project.payload.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Tên đăng nhập được yêu cầu")
    private String username;
    @NotBlank(message="Mật khẩu được yêu cầu")
    String password;
}
