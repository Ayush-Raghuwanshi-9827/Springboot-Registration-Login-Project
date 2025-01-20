package com.Users.Services;

import com.Users.Entity.User;
import com.Users.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public String generateResetToken(String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String token = UUID.randomUUID().toString();
        user.setRestToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(30));
        userRepo.save(user);

        return token;
    }

    public void resetPassword(String token, String newPassword){
        User user = userRepo.findByResetToken(token).orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRestToken(null);
        user.setTokenExpiry(null);
        userRepo.save(user);
    }
}
