package com.stu.musicapp.model;

public class AccountModel {
    String id;
    String username;
    String password;
    String displayName;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkImage() {
        return linkImage;
    }

    String numberPhone;

    String email;
    String linkImage;

    public AccountModel(String id, String username, String password, String displayName, String numberPhone, String email, String linkImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.numberPhone = numberPhone;
        this.email = email;
        this.linkImage = linkImage;
    }
}
