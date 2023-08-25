package com.faq.common.Entities;

import com.faq.common.Requests.AccountRequest;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class AccountEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public String getPassword() {
        return password;
    }
}