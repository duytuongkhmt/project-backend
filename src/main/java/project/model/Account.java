package project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
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
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String profileId;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonBackReference
    private Conversation conversation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Follow> followers;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;


    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> friendRequestsSent;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> friendRequestsReceived;

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


    public enum Role {
        ADMIN, USER, ARTIST;

        public static Role fromString(String role) {
            for (Role r : Role.values()) {
                if (r.name().equalsIgnoreCase(role)) {
                    return r;
                }
            }
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}

