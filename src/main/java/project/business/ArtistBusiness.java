package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.mapper.UserMapper;
import project.model.Account;
import project.model.Profile;
import project.resource.ProfileResource;
import project.service.ProfileService;
import project.service.UserService;
import project.util.AuthUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArtistBusiness {

    private final ProfileService profileService;
    private final UserService userService;

    public List<ProfileResource> getTopArtists() {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        String myProfileId = account.getProfile().getId();
        List<Profile> topArtists = profileService.getTopArtist(myProfileId);
        return topArtists.stream().map(UserMapper::map).toList();
    }
}
