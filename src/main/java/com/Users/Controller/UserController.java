package com.Users.Controller;

import com.Users.Entity.User;
import com.Users.Repository.UserRepo;
import com.Users.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
   private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody Map<String, String> request){
        String email = request.get("email");
        String token = userService.generateResetToken(email);

        System.out.println("Reset Token: "+token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password reset Token sent to your email!");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request){
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        userService.resetPassword(token, newPassword);
        return ResponseEntity.status(HttpStatus.OK).body("Password successfully Reset!");

    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        if (userService.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Email already Registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
            return ResponseEntity.status(HttpStatus.OK).body(user.getEmail());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid Credentials");
        }
    }




}
