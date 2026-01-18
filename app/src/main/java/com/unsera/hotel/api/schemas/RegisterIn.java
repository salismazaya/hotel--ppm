package com.unsera.hotel.api.schemas;

public class RegisterIn {
    String username;
    String password;
    String fullname;
    public RegisterIn(String u, String p, String f) { this.username = u; this.password = p; this.fullname = f; }
}