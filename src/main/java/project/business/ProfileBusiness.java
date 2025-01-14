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
import project.resource.ProfileResource;
import project.resource.ReviewResource;
import project.resource.SearchHistoryResource;
import project.service.*;
import project.util.AuthUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ProfileBusiness {

    private final ProfileService profileService;
    private final UserService userService;
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
        String username = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();
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
        String username = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();
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


    public List<ProfileResource> search(SearchRequest request) {
        List<Profile> profiles = profileService.getProfileBySearch(request);
        return profiles.stream().map(UserMapper::map).toList();
    }

    public Page<ProfileResource> search(SearchRequest request, PageRequest pageRequest) {
        Page<Profile> profiles = profileService.getProfileBySearch(request,pageRequest);
        return profiles.map(UserMapper::map);
    }

    public List<SearchHistoryResource> getHistory() {
        String username = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();
        List<SearchHistory> searchHistories = searchHistoryService.getHistories(profile.getId());
        return searchHistories.stream().map(s -> {
            SearchHistoryResource resource = new SearchHistoryResource();
            BeanUtils.copyProperties(s, resource);
            return resource;
        }).toList();
    }

    public void saveHistory(String key) {
        String username = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(username);
        Profile profile = account.getProfile();

        SearchHistory searchHistory=new SearchHistory();
        searchHistory.setKey(key);
        searchHistory.setUserId(profile.getId());
        searchHistoryService.save(searchHistory);
    }
}
