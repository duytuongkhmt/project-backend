package project.util;


import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    private AuthUtils() {
    }

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
