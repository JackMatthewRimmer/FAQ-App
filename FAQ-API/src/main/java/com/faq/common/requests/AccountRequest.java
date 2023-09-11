package com.faq.common.requests;

import com.faq.common.exceptions.ApiException;

import static com.faq.common.exceptions.ApiException.ApiErrorType.*;

public class AccountRequest implements Request {

    private String email;

    private String password;

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
            throw new ApiException(ACCOUNT_MISSING_EMAIL, AccountRequest.class);
        }

        if (this.password == null) {
            throw new ApiException(ACCOUNT_MISSING_PASSWORD, AccountRequest.class);
        }

        if (!isEmail(this.email)) {
            throw new ApiException(ACCOUNT_INVALID_EMAIL, AccountRequest.class);
        }
        return true;
    }

    private boolean isEmail(String email) {
        return email.contains("@");
    }
}
