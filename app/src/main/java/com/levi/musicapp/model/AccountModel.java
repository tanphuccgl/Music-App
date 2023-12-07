package com.levi.musicapp.model;

public class AccountModel {
    String id;
    String username;
    String password;
    String displayName;
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
