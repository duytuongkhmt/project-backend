package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.business.ProfileBusiness;
import project.payload.request.user.ProfileUpdateRequest;
import project.payload.response.ResponseObject;
import project.resource.ProfileResource;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileBusiness profileBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> getProfile() {
        ProfileResource result = profileBusiness.getProfile();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseObject> getProfileByUsername(@PathVariable String username) {
        ProfileResource result = profileBusiness.getByUsername(username);
        return ResponseEntity.ok(ResponseObject.ok(result));
    }
    @PostMapping("/update")
    public ResponseEntity<ResponseObject> updateProfile(@RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.update(request)));

    }

    @PutMapping("/upload-avatar")
    public ResponseEntity<ResponseObject> uploadAvatar(@RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.saveAvatar(avatar)));
    }

    @PutMapping("/upload-photo-cover")
    public ResponseEntity<ResponseObject> uploadCoverPhoto(@RequestParam(value = "photo-cover", required = false) MultipartFile photoCover) {
        return ResponseEntity.ok(ResponseObject.ok(profileBusiness.saveCoverPhoto(photoCover)));
    }
}
