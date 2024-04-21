package project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account implements UserDetails, Serializable {
    private static final long serialVersionUID = -297553281792804396L;
    @Id
    @UuidGenerator
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String mobile;
    private String email;
    private String rememberToken;
    private LocalDate emailVerifiedAt;
    private LocalDate confirmedAt;
    private LocalDateTime expiresAt;
    private String confirmationToken;
    private String activeToken;
    private String verifyToken;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer activeByEmail;
    private Integer role;
    private Boolean isArtist;
    private String bio;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static class ROLE{
        public static Integer ADMIN = 0;
        public static Integer USER = 1;
        public static Integer ARTIST = 2;
    }
    public static class ACTIVE_BY_EMAIL {
        public static Integer PENDING = 0;
        public static Integer ACTIVE = 1;
    }
}
