package project.service;


import lombok.RequiredArgsConstructor;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.model.entity.Account;
import project.model.entity.Profile;
import project.payload.request.user.SearchRequest;
import project.repository.ProfileRepository;

import static project.repository.spec.ProfileSpecification.*;

import java.util.List;

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

    public Profile findByProfileCode(String code) {
        return profileRepository.findByProfileCode(code);
    }

    public List<Profile> findAll() {
        return profileRepository.findAllByBioIsNotNull();
    }

    public List<Profile> findByIds(List<String> ids) {
        return profileRepository.findAllByIdIn(ids);
    }

    public List<Profile> getTopArtist(String profileId) {
        return profileRepository.findAll(
                where(notIsProfileId(profileId))
                        .and(roleIs(Account.Role.ARTIST.name()))
                        .and(isActive()));
    }

    public List<Profile> getAllActiveProfileExceptionMe(String profileId) {
        return profileRepository.findAll(
                where(notIsProfileId(profileId))
                        .and(isActive()));
    }

    public List<Profile> getProfileBySearch(SearchRequest request) {
        return profileRepository.findAll(
                where(fullNameContain(request.getKey()))
                        .and(roleIs(request.getRole()))
                        .and(isActive())
                        .and(rateGreaterThanOrEqualTo(request.getRate()))
                        .and(genreLike(request.getCategory()))
        );
    }

    public Page<Profile> getProfileBySearch(SearchRequest request, PageRequest pageRequest) {
        return profileRepository.findAll(
                where(fullNameContain(request.getKey()))
                        .and(roleIs(request.getRole()))
                        .and(isActive())
                        .and(rateGreaterThanOrEqualTo(request.getRate()))
                        .and(genreLike(request.getCategory()))
                , pageRequest);
    }
}
