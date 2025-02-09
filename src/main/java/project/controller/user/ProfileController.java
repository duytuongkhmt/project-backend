package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.business.ProfileBusiness;
import project.payload.request.user.BankUpdateRequest;
import project.payload.request.user.ProfileUpdateRequest;
import project.payload.response.ResponseObject;
import project.resource.ProfileResource;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileBusiness profileBusiness;

    @GetMapping("/search/{key}")
    public ResponseEntity<ResponseObject> getProfilesByKey(@PathVariable String key) {
        ProfileResource result = profileBusiness.getProfileByCode(key);
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseObject> getProfileByCode(@PathVariable String code) {
        ProfileResource result = profileBusiness.getProfileByCode(code);
        return ResponseEntity.ok(ResponseObject.ok(result));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateProfile(@RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.update(request)));

    }

    @PutMapping("/upload-avatar")
    public ResponseEntity<ResponseObject> uploadAvatar(@RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.saveAvatar(avatar)));
    }

    @PutMapping("/upload-cover-photo")
    public ResponseEntity<ResponseObject> uploadCoverPhoto(@RequestParam(value = "cover-photo", required = false) MultipartFile photoCover) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.saveCoverPhoto(photoCover)));
    }

    @PutMapping("/upload-qr-bank")
    public ResponseEntity<ResponseObject> uploadQRBank(@RequestParam(value = "qr", required = false) MultipartFile photoCover) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.uploadQR(photoCover)));
    }

    @PutMapping("/update-bank")
    public ResponseEntity<ResponseObject> updateBankInfo(@RequestBody BankUpdateRequest request){
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.saveBankInfo(request)));
    }
}
