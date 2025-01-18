package project.mapper;

import org.springframework.beans.BeanUtils;
import project.model.entity.Account;
import project.model.entity.Bank;
import project.model.entity.Profile;
import project.resource.BankResource;
import project.resource.FriendshipResource;
import project.resource.ProfileResource;
import project.resource.UserResource;

public class UserMapper {
    private UserMapper() {

    }

    public static UserResource map(Account user) {
        UserResource usersResource = new UserResource();
        BeanUtils.copyProperties(user, usersResource);
        usersResource.setRole(user.getRole().name());
        usersResource.setAvatar(user.getProfile().getAvatar());
        usersResource.setProfileCode(user.getProfile().getProfileCode());
        usersResource.setProfileId(user.getProfile().getId());

        return usersResource;
    }

    public static ProfileResource map(Profile profile,Integer totalFriend) {
        ProfileResource profileResource = new ProfileResource();
        BeanUtils.copyProperties(profile, profileResource);
        profileResource.setRole(profile.getUser().getRole().name());
        profileResource.setEmail(profile.getUser().getEmail());
        profileResource.setMobile(profile.getUser().getMobile());
        profileResource.setJoinDay(profile.getUser().getConfirmedAt());
        Bank bank = profile.getBank();
        if (bank != null) {
            BankResource resource = new BankResource();
            BeanUtils.copyProperties(bank, resource);
            profileResource.setBank(resource);
        } else {
            profileResource.setBank(null);
        }
        profileResource.setTotalFriend(totalFriend);
        return profileResource;
    }
    public static ProfileResource map(Profile profile) {
        ProfileResource profileResource = new ProfileResource();
        BeanUtils.copyProperties(profile, profileResource);
        profileResource.setRole(profile.getUser().getRole().name());
        profileResource.setEmail(profile.getUser().getEmail());
        profileResource.setMobile(profile.getUser().getMobile());
        profileResource.setJoinDay(profile.getUser().getConfirmedAt());
        Bank bank = profile.getBank();
        if (bank != null) {
            BankResource resource = new BankResource();
            BeanUtils.copyProperties(bank, resource);
            profileResource.setBank(resource);
        } else {
            profileResource.setBank(null);
        }
        return profileResource;
    }

    public static ProfileResource map(Profile profile, FriendshipResource friendshipResource) {
        ProfileResource profileResource = map(profile);
        profileResource.setFriendship(friendshipResource);
        return profileResource;
    }
}
