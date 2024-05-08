package br.com.hyzed.hyzedapi.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonBackReference
    @Column(nullable = false)
    private String password;
    @JsonBackReference
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(RegisterRequestDTO data) {
        BeanUtils.copyProperties(data, this);
    }

    @JsonBackReference
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @JsonBackReference
    @Override
    public String getUsername() {
        return email;
    }

    @JsonBackReference
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonBackReference
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonBackReference
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonBackReference
    @Override
    public boolean isEnabled() {
        return true;
    }
}
