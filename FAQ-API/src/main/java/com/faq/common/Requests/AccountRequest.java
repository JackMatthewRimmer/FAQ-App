package com.faq.common.Requests;


import com.faq.common.Exceptions.ApiException;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class AccountRequest implements Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean validate() throws ApiException {
        if (this.email == null) {
            throw new ApiException(ApiException.ApiErrorType.ACCOUNT_MISSING_EMAIL);
        }

        if (this.password == null) {
            throw new ApiException(ApiException.ApiErrorType.ACCOUNT_MISSING_PASSWORD);
        }

        if (!isEmail(this.email)) {
            throw new ApiException(ApiException.ApiErrorType.ACCOUNT_INVALID_EMAIL);
        }
        return true;
    }

    private boolean isEmail(String email) {
        return email.contains("@");
    }
}
