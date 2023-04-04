package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);
        sessionService.create(session);
        verify(sessionRepository).save(session);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        sessionService.delete(id);
        verify(sessionRepository).deleteById(id);
    }

    @Test
    public void testFindAll() {
        List<Session> sessions = Arrays.asList(new Session(), new Session());
        when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> result = sessionService.findAll();
        assertEquals(sessions, result);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Session session = new Session();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        Session result = sessionService.getById(id);
        assertEquals(session, result);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);
        sessionService.update(id, session);
        verify(sessionRepository).save(session);
    }

    @Test
    public void testParticipate() {
        Long id = 1L;
        Long userId = 2L;
        Session session = new Session();
        User user = new User();
        user.setId(userId);

        List<User> users = new ArrayList<>();
        session.setUsers(users);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        sessionService.participate(id, userId);

        verify(sessionRepository).save(session);
        assertTrue(session.getUsers().contains(user));
    }

    @Test
    public void testNoLongerParticipate() {
        Long id = 1L;
        Long userId = 2L;
        Session session = new Session();
        User user = new User();
        user.setId(userId);

        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        sessionService.noLongerParticipate(id, userId);

        verify(sessionRepository).save(session);
        assertFalse(session.getUsers().contains(user));

    }
}
