package com.collabhub.backend.dto;

public class RegisterRequest {

    private String email;
    private String password;
    private String full_name;

    // Default constructor
    public RegisterRequest() {}

    // All-args constructor
    public RegisterRequest(String email, String password, String full_name) {
        this.email = email;
        this.password = password;
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
