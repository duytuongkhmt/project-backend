package project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Profile;
import project.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile getProfileById(String id) {
        return profileRepository.findById(id).orElse(null);
    }
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }
}
