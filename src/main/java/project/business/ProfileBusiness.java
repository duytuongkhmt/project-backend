package project.business;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.common.Constant;
import project.model.Account;
import project.model.Profile;
import project.payload.request.user.ProfileUpdateRequest;
import project.resource.ProfileResource;
import project.service.ProfileService;
import project.service.UserService;
import project.util.AuthUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ProfileBusiness {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileResource getByUsername(String username) {
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();
        ProfileResource profileResource = new ProfileResource();
        BeanUtils.copyProperties(profile, profileResource);
        return profileResource;
    }


    public ProfileResource getProfile() {
        String username = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();
        ProfileResource profileResource = new ProfileResource();
        BeanUtils.copyProperties(profile, profileResource);
        profileResource.setFullName(profile.getUser().getFullName());
        profileResource.setRole(profile.getUser().getRole().name());
        return profileResource;
    }


    public ProfileResource update(ProfileUpdateRequest request) {
        Profile profile = profileService.getProfileById(request.getId());
        BeanUtils.copyProperties(request, profile);

        profile = profileService.save(profile);
        ProfileResource profileResource = new ProfileResource();
        BeanUtils.copyProperties(profile, profileResource);
        return profileResource;

    }

    // Lưu avatar
    public String saveAvatar(MultipartFile avatar) {
        return processUpload(avatar, "avatar");
    }

    // Lưu ảnh bìa
    public String saveCoverPhoto(MultipartFile photoCover) {
        return processUpload(photoCover, "cover-photo");
    }

    private String processUpload(MultipartFile file, String type) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException(type + " file is required.");
        }

        // Kiểm tra định dạng tệp
        Pattern pattern = Pattern.compile("(.+)\\.(png|gif|jpg|jpeg)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        if (!matcher.matches()) {
            throw new RuntimeException("You can only upload image files.");
        }

        // Đường dẫn lưu tệp
        String fileName = AuthUtils.getCurrentUsername()+"_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String destinationPath = Constant.UPLOAD_DIR + "/" + type + "/" + fileName;

        try {
            File destinationFile = new File(destinationPath);
            destinationFile.getParentFile().mkdirs(); // Tạo thư mục nếu chưa tồn tại
            file.transferTo(destinationFile); // Lưu tệp

            saveUrl(destinationFile,type);

            // Lưu thông tin đường dẫn tệp vào DB (có thể cập nhật hồ sơ người dùng ở đây)
            // profile.setAvatarUrl(destinationPath); (hoặc tương tự)
            return destinationPath; // Trả về đường dẫn hoặc URL
        } catch (IOException e) {
            throw new RuntimeException("Failed to save " + type + " file.", e);
        }
    }
    private void saveUrl( File destinationFile,String type) {
        String username = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();
        if(type.equals("avatar")){
            profile.setAvatar(destinationFile.getAbsolutePath());
        }
        else {
            profile.setCoverPhoto(destinationFile.getAbsolutePath());
        }
        profileService.save(profile);
    }
}
