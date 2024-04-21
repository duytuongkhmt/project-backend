package project.business;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.mapper.UsersMapper;
import project.model.Account;
import project.payload.request.auth.LoginRequest;
import project.resource.UsersResource;
import project.sercurity.JwtService;
import project.service.AuthenticationService;
import project.service.GoogleService;
import project.service.UsersService;


@RequiredArgsConstructor
@Component
public class AuthenticateBusiness {
    private final UsersService usersService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final GoogleService googleService;

    public Account getUser(String userName) {
        return usersService.findByUsername(userName);
    }


    public String auth(LoginRequest request, Account users) {
        return authenticationService.login(request, users);
    }

    public String auth( Account users) {
        return authenticationService.login(users);
    }

    public UsersResource getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account user = usersService.findByUsername(username);
        return UsersMapper.map(user);
    }

    public String authByGoogle(String code, String redirectUri) {
        String token = googleService.getAccessToken(code, redirectUri);
        String email = googleService.getEmail(token);
        Account user = usersService.findByEmail(email);
        if (user == null) {
            return "This email is not registered to any account";
        }
        return jwtService.generateToken(user);
    }

}
