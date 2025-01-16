package project.util;


import org.springframework.security.core.context.SecurityContextHolder;
import project.model.entity.Account;
import project.model.entity.Profile;

public class AuthUtils {
    private AuthUtils() {
    }

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public static String getCurrentProfileId(){
        Account account= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return account.getProfile().getId();
    }

    public static Profile getCurrentProfile(){
        Account account= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return account.getProfile();
    }

    public static Account getCurrentAccount(){
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
