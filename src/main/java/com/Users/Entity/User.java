package com.Users.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;

    private String resetToken;

    private LocalDateTime tokenExpiry;

    public String getRestToken() {
        return resetToken;
    }

    public void setRestToken(String restToken) {
        this.resetToken = restToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", restToken='" + resetToken + '\'' +
                ", tokenExpiry=" + tokenExpiry +
                '}';
    }

    public User(Integer userId, String email, String password, String fullName, String resetToken, LocalDateTime tokenExpiry) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.resetToken = resetToken;
        this.tokenExpiry = tokenExpiry;
    }

    public User(){

    }
}
