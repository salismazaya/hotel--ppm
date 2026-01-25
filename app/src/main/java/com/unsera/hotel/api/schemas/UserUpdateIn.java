package com.unsera.hotel.api.schemas;


public class UserUpdateIn {
    String fullname;
    String username;

    public UserUpdateIn(String fullname, String email) {
        this.fullname = fullname;
        this.username = email;
    }
}
