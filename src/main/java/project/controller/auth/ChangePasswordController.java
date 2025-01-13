package project.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.business.UserBusiness;
import project.payload.request.auth.UpdatePasswordRequest;
import project.payload.response.ResponseObject;

@RestController
@RequestMapping("/api/v1/auth/reset-password")
@RequiredArgsConstructor
public class ChangePasswordController {
    private final UserBusiness userBusiness;
    @PostMapping("/update")
    public ResponseEntity<ResponseObject> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        return ResponseEntity.ok(ResponseObject.ok(userBusiness.changePassword(updatePasswordRequest)));
    }
}
