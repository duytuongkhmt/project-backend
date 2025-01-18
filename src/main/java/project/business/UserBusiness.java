package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.config.MD5PasswordEncoder;
import project.exception.InvalidDataException;
import project.mapper.UserMapper;
import project.model.entity.Account;
import project.model.entity.Friendship;
import project.model.entity.Profile;
import project.payload.request.auth.UpdatePasswordRequest;
import project.resource.FriendshipResource;
import project.resource.ProfileResource;
import project.resource.UserResource;
import project.service.FriendshipService;
import project.service.ProfileService;
import project.service.UserService;
import project.util.AuthUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBusiness {
    private final UserService userService;
    private final ProfileService profileService;


    public List<ProfileResource> getAllProfile() {
        List<Profile> profiles = profileService.findAll();
        return profiles.stream().map(UserMapper::map).toList();
    }


    private final MD5PasswordEncoder passwordEncoder;

    public void changePassword(UpdatePasswordRequest updatePasswordRequest) {
        Account myAccount = AuthUtils.getCurrentAccount();
        if (!passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(), myAccount.getPassword())) {
            throw new InvalidDataException("The old password is incorrect");
        }
        if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match");
        }
        myAccount.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userService.save(myAccount);
    }

}
