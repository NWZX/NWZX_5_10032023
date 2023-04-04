package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {
    private UserService userService;
    private UserMapper userMapper;
    private UserController userController;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    private User user;
    private UserDto userDto;
    private List<User> users;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);

        user = new User()
                .setId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        users = Arrays.asList(user);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user); // or any valid Principal object
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findById() {
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        ResponseEntity<?> response = userController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());

        verify(userService).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    void findById_invalidId() {
        ResponseEntity<?> response = userController.findById("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).findById(any());
    }

    //@Test
    void save() {
        when(userService.findById(1L)).thenReturn(user);

        ResponseEntity<?> response = userController.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userService).findById(1L);
        verify(userService).delete(1L);
    }

    @Test
    void save_invalidId() {
        ResponseEntity<?> response = userController.save("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).findById(any());
        verify(userService, never()).delete(any());
    }
}
