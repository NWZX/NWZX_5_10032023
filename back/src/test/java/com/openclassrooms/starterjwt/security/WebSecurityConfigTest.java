package com.openclassrooms.starterjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.security.jwt.AuthEntryPointJwt;
import com.openclassrooms.starterjwt.security.jwt.AuthTokenFilter;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WebSecurityConfigTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private AuthEntryPointJwt unauthorizedHandler;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthTokenFilter authTokenFilter;

    @MockBean
    private JwtUtils jwtUtils;



    //@Test
    @WithAnonymousUser
    public void testApiAuthLoginWithoutAuthentication() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("test");
        String bodyContent = new ObjectMapper().writeValueAsString(loginRequest);

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(jwtUtils.generateJwtToken(any())).thenReturn("mockJwtToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andExpect(status().isOk());
    }


    //@Test
    @WithAnonymousUser
    public void testApiAuthRegisterWithoutAuthentication() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@test.com");
        signupRequest.setFirstName("TestFirstName");
        signupRequest.setLastName("TestLastName");
        signupRequest.setPassword("testpassword");

        String bodyContent = new ObjectMapper().writeValueAsString(signupRequest);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andExpect(status().isOk());
    }

    //@Test
    @WithAnonymousUser
    public void testApiEndpointsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/session"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/session"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put("/api/session/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/session/1/participate/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }

    //@Test
    @WithMockUser
    public void testApiEndpointsWithAuthentication() throws Exception {
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/session"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/session/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/session/1/participate/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/session/1/participate/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isOk());
    }
}
