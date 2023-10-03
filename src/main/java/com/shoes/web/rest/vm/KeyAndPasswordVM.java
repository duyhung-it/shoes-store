package com.shoes.web.rest.vm;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    private String resetKey;

    private String passwordHash;

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String key) {
        this.resetKey = key;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String newPassword) {
        this.passwordHash = newPassword;
    }
}
