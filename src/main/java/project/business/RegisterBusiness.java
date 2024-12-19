package project.business;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.config.MD5PasswordEncoder;
import project.model.entity.Account;
import project.model.entity.Bank;
import project.model.entity.Profile;
import project.payload.request.auth.RegisterRequest;
import project.service.UserService;
import project.service.email.EmailSender;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;


@Slf4j
@RequiredArgsConstructor
@Component
public class RegisterBusiness {
    private final UserService usersService;
    private final MD5PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    @Value("${verify.account.url}")
    private String urlVerify;
    public String register(RegisterRequest registerRequest) {
        String token = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        saveUserInfo(registerRequest, token);

        CompletableFuture.runAsync(() -> {
            Map<String, Object> variables = new HashMap<>();

            String link = urlVerify + token;
            variables.put("name", registerRequest.getFullName());
            variables.put("link", link);
            emailSender.send(
                    "no-reply@adflex.vn",
                    registerRequest.getEmail(),
                    "register-mail-form-template.html",
                    variables,
                    "Confirm your email"
            );
        });

        return token;
    }

    @Async
    protected void saveUserInfo(RegisterRequest registerRequest, String token) {
        LocalDateTime time = LocalDateTime.now();

        Account user = Account.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .role(registerRequest.getRole())
                .modifiedAt(time)
                .mobile(registerRequest.getMobile())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .confirmationToken(token)
                .createdAt(time)
                .expiresAt(time.plusMinutes(15))
                .build();


        // Tạo đối tượng Profile
        Profile profile = Profile.builder()
                .bio("Welcome to my profile!")
                .coverPhoto(null)
                .genre(new ArrayList<>())
                .rate(0.0)
                .price(0.0)
                .profileCode(generateRandomNumberString(10))
                .note(null)
                .build();
        Bank bank=new Bank();
        profile.setBank(bank);
        bank.setProfile(profile);
        user.setProfile(profile);
        profile.setUser(user);
        usersService.save(user);
    }

    @Transactional
    public String confirmToken(String token) {
        Account user = usersService.findByConfirmationToken(token);
        if (user == null) {
            return "Invalid token";
        }

        if (user.getConfirmedAt() != null) {
            return "email already confirmed";
        }
        LocalDateTime expiredAt = user.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "token expired";
        }
        user.setCreatedAt(LocalDateTime.now());
        usersService.setConfirmedAt(token);
        usersService.enableUser(user.getEmail());
        user.setIsEmailVerified(true);
        usersService.save(user);

        return "confirmed";
    }
    private String generateRandomNumberString(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            result.append(digit);
        }

        return result.toString();
    }
}
