package com.faq.common.Entities;

import com.faq.common.Requests.AccountRequest;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class AccountEntity implements UserDetails {

    @Id
    @Column(name="users_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long users_id;

    @NaturalId
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    public AccountEntity(String email, String hashedPassword) {
        this.email = email;
        this.password = hashedPassword;
    }

    public AccountEntity(AccountRequest accountRequest) {
        this.email = accountRequest.getEmail();
        this.password = accountRequest.getPassword();
    }

    public AccountEntity() {}

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // You can change the role as needed
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
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
}