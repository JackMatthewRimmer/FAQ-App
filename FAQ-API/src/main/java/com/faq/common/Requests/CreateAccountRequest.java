package com.faq.common.Requests;


import com.faq.common.Requests.Request;

public class CreateAccountRequest implements Request {

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
    public boolean validate() {
        if (this.email == null) {
            return false;
        }

        if (this.password == null) {
            return false;
        }

        return isEmail(this.email);
    }

    private boolean isEmail(String email) {
        return email.contains("@");
    }
}
