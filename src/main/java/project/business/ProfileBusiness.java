package project.business;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.common.Constant;
import project.mapper.UserMapper;
import project.model.entity.*;
import project.payload.request.user.BankUpdateRequest;
import project.payload.request.user.ProfileUpdateRequest;
import project.payload.request.user.SearchRequest;
import project.resource.FriendshipResource;
import project.resource.ProfileResource;
import project.resource.SearchHistoryResource;
import project.service.*;
import project.util.AuthUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ProfileBusiness {

    private final ProfileService profileService;
    private final FriendshipService friendshipService;
    private final SearchHistoryService searchHistoryService;

    public ProfileResource getProfileByCode(String code) {
        Profile profile = profileService.findByProfileCode(code);
        Account account = profile.getUser();
        List<Account> friends = friendshipService.getFriends(account);
        return UserMapper.map(profile, friends.size());
    }

    public ProfileResource getById(String id) {
        Profile profile = profileService.getProfileById(id);
        return UserMapper.map(profile);
    }


    @Transactional
    public ProfileResource update(ProfileUpdateRequest request) {
        Profile profile = profileService.getProfileById(request.getId());
        BeanUtils.copyProperties(request, profile);
        profile.getUser().setMobile(request.getMobile());
        profile.setFullName(request.getFullName());
        profile = profileService.save(profile);
        return UserMapper.map(profile);
    }

    public ProfileResource saveBankInfo(BankUpdateRequest request) {
        Profile profile = AuthUtils.getCurrentProfile();
        Bank bank = profile.getBank();
        if (bank == null) {
            bank = new Bank();
            bank.setProfile(profile);
        }
        BeanUtils.copyProperties(request, bank);
        profile.setBank(bank);
        profileService.save(profile);
        return UserMapper.map(profile);
    }

    // Lưu avatar
    public String saveAvatar(MultipartFile avatar) {
        return processUpload(avatar, "avatar");
    }

    // Lưu ảnh bìa
    public String saveCoverPhoto(MultipartFile photoCover) {
        return processUpload(photoCover, "cover-photo");
    }

    public String uploadQR(MultipartFile qr) {
        return processUpload(qr, "qr");
    }

    private String processUpload(MultipartFile file, String type) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException(type + " file is required.");
        }

        Pattern pattern = Pattern.compile("(.+)\\.(png|gif|jpg|jpeg|webp|svg)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        if (!matcher.matches()) {
            throw new RuntimeException("You can only upload image files.");
        }

        String fileName = AuthUtils.getCurrentUsername() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String destinationPath = Constant.UPLOAD_DIR + "/" + type + "/" + fileName;

        try {
            File destinationFile = new File(destinationPath);
            destinationFile.getParentFile().mkdirs();
            file.transferTo(destinationFile);

            saveUrl(destinationFile, type);

            return destinationPath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save " + type + " file.", e);
        }
    }

    private void saveUrl(File destinationFile, String type) {

        Profile profile = AuthUtils.getCurrentProfile();
        if (type.equals("avatar")) {
            profile.setAvatar(destinationFile.getAbsolutePath());
        } else if (type.equals("cover-photo")) {
            profile.setCoverPhoto(destinationFile.getAbsolutePath());
        } else {
            Bank bank = profile.getBank();
            if (bank == null) {
                bank = new Bank();
            }
            bank.setQr(destinationFile.getAbsolutePath());
            bank.setProfile(profile);
            profile.setBank(bank);
        }
        profileService.save(profile);
    }


    public Page<ProfileResource> search(SearchRequest request, PageRequest pageRequest) {
        Page<Profile> profiles = profileService.getProfileBySearch(request, pageRequest);
        Map<String, FriendshipResource> map = new HashMap<>();
        profiles.stream().forEach(profile -> {
                    FriendshipResource friendshipResource = checkStatusFriend(profile.getId());
                    if (friendshipResource.getRequester() != null) {
                        map.put(profile.getId(), friendshipResource);
                    }
                }
        );
        return profiles.map(profile -> {
            if(map.containsKey(profile.getId())) {
                return UserMapper.map(profile, map.get(profile.getId()));
            }
            else return UserMapper.map(profile);
        });
    }

    public List<SearchHistoryResource> getHistory() {

        List<SearchHistory> searchHistories = searchHistoryService.getHistories(AuthUtils.getCurrentProfileId());
        return searchHistories.stream().map(s -> {
            SearchHistoryResource resource = new SearchHistoryResource();
            BeanUtils.copyProperties(s, resource);
            return resource;
        }).toList();
    }

    public void saveHistory(String key) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setKey(key);
        searchHistory.setUserId(AuthUtils.getCurrentProfileId());
        searchHistoryService.save(searchHistory);
    }

    private FriendshipResource checkStatusFriend(String friendId) {
        Account sender = AuthUtils.getCurrentAccount();
        Account receiver = profileService.getProfileById(friendId).getUser();
        FriendshipResource friendshipResource = new FriendshipResource();
        Friendship friendship1 = friendshipService.findBySenderAndReceiver(sender, receiver);
        Friendship friendship2 = friendshipService.findBySenderAndReceiver(receiver, sender);
        if (friendship1 != null) {
            friendshipResource.setStatus(friendship1.getStatus());
            friendshipResource.setRequester(friendship1.getSender().getProfile().getId());
        }
        if (friendship2 != null) {
            friendshipResource.setStatus(friendship2.getStatus());
            friendshipResource.setRequester(friendship2.getSender().getProfile().getId());
        }
        return friendshipResource;
    }
}
