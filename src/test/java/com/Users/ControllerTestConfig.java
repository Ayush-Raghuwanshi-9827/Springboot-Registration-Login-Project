package com.Users;

import com.Users.Repository.UserRepo;
import com.Users.Services.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class ControllerTestConfig {

   @Bean
    public UserRepo userRepo(){
       return mock(UserRepo.class);
   }

   @Bean
   public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

   @Bean
   public UserService userService(){
       return mock(UserService.class);
   }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
       httpSecurity
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeRequests().anyRequest().permitAll();
       return httpSecurity.build();
    }





}
