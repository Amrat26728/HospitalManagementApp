package com.amrat.HospitalManagementApp.entities;

import com.amrat.HospitalManagementApp.entities.types.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "app_user"
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private boolean isVerified;

    private boolean isActive;

    @ElementCollection(fetch = FetchType.EAGER) // this will create new table roles
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()))
        );
        return authorities;
    }

    public User(String username, String password, Set<RoleType> roles){

        if (username == null || username.isEmpty()){
            throw new IllegalArgumentException("Username is required");
        }

        if (password == null || password.isEmpty()){
            throw new IllegalArgumentException("Password are required");
        }

        if (roles == null || roles.isEmpty()){
            throw new IllegalArgumentException("Roles are required");
        }

        this.username = username;
        this.password = password;
        this.isVerified = false;
        this.roles = new HashSet<>(roles);
        this.isActive = true;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void setVerified(){
        this.isVerified = true;
    }

    public void setActive(){
        this.isActive = false;
    }
}
