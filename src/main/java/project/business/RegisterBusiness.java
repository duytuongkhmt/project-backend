package project.business;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.config.MD5PasswordEncoder;
import project.model.Account;
import project.payload.request.auth.RegisterRequest;
import project.service.UsersService;
import project.service.email.EmailSender;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;


@Slf4j
@RequiredArgsConstructor
@Component
public class RegisterBusiness {
    private final UsersService usersService;
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
                .activeByEmail(0)
                .confirmationToken(token)
                .createdAt(time)
                .expiresAt(time.plusMinutes(15))
                .build();
        if(Objects.equals(registerRequest.getRole(), Account.ROLE.ARTIST)){
            user.setIsArtist(true);
        }
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
//        user.setActiveByEmail();
        usersService.setConfirmedAt(token);
        usersService.enableUser(user.getEmail());
        user.setActiveByEmail(Account.ACTIVE_BY_EMAIL.ACTIVE);
        usersService.save(user);

        return "confirmed";
    }

}
