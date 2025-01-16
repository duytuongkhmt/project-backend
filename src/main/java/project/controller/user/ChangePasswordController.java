package project.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.business.UserBusiness;
import project.payload.request.auth.UpdatePasswordRequest;
import project.payload.response.ResponseObject;

@Controller
@RequestMapping("/api/v1/user/change-password")
@RequiredArgsConstructor
public class ChangePasswordController {
    private final UserBusiness userBusiness;
    @PostMapping
    public ResponseEntity<ResponseObject> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        userBusiness.changePassword(updatePasswordRequest);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }
}
