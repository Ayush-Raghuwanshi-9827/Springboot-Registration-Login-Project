package com.Users;

import com.Users.Controller.UserController;
import com.Users.Entity.User;
import com.Users.Services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {ControllerTestConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testSignup_Success() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encrypted-password");

        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-password");
        when(userService.save(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}").with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.password").value("encrypted-password"));
    }


    @Test
    public void testForgetPassword_Success() throws Exception {
        when(userService.generateResetToken(anyString())).thenReturn("test-reset-token");

        mockMvc.perform(post("/api/auth/forget-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").value("Password reset Token sent to your email!"));
    }

    @Test
    public void testLogin_Success() throws Exception {
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("test@example.com"));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(Mockito.any())).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid Credentials"));
    }

    @Test
    public void testSignup_EmailAlreadyRegistered() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        when(userService.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Email already Registered"));
    }


    @Test
    public void testResetPassword_Success() throws Exception {
        Mockito.doNothing().when(userService).resetPassword(anyString(), anyString());

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"test-reset-token\", \"newPassword\":\"newpassword123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Password successfully Reset!"));
    }


}
