package project.payload.request.auth;

import jakarta.validation.constraints.*;
import lombok.Getter;
import project.model.Account;
import project.validator.ExitUser;

@Getter
@ExitUser.List({
        @ExitUser(column = "email", dbColumn = "email",message = "Email exist"),
        @ExitUser(column = "username", dbColumn = "id", message = "Username exist")
//        @ExitUser(column = "mobile", dbColumn = "mobile", message = "Phone exist")
})
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 24, message = "Tên đăng nhập phải có độ dài từ 6 đến 24 kí tự")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Tên đăng nhập chỉ bao gồm các chữ cái viết thường và chữ số")
    private String username;
//    @NotBlank(message="Tên là trường bắt buộc")
    @Size(min = 3, message = "Tên phải có ít nhất 3 kí tự")
    private String fullName;
    @Email(message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Mật khẩu là trường bắt buộc")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 kí tự")
    private String password;
    @NotBlank(message = "Xác nhận mật khẩu là trường bắt buộc")
    @Size(min = 6, message = "Xác nhận mật khẩu phải có ít nhất 6 kí tự")
    private String confirmPassword;
//    @NotBlank(message = "Số điện thoại là trường bắt buộc")
    private String mobile;
    @NotNull(message = "Role là trường bắt buộc")
    private String role;
    public Account.Role getRole() {
        return Account.Role.fromString(role);
    }
}
