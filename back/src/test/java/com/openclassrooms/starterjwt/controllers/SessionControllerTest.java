package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private SessionController sessionController;

    private Session session;
    private SessionDto sessionDto;
    private List<Session> sessions;

    @BeforeEach
    void setUp() {
        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Test Description");

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Test Session");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Test Description");

        sessions = Arrays.asList(session);
    }

    @Test
    void findById() {
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

        verify(sessionService).getById(1L);
        verify(sessionMapper).toDto(session);
    }

    @Test
    void findById_invalidId() {
        ResponseEntity<?> response = sessionController.findById("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).getById(any());
    }

    @Test
    void findAll() {
        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(Arrays.asList(sessionDto));

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(sessionDto), response.getBody());

        verify(sessionService).findAll();
        verify(sessionMapper).toDto(sessions);
    }

    @Test
    void create() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

        verify(sessionMapper).toEntity(sessionDto);
        verify(sessionService).create(session);
        verify(sessionMapper).toDto(session);
    }

    @Test
    void update() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(1L, session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

        verify(sessionMapper).toEntity(sessionDto);
        verify(sessionService).update(1L, session);
        verify(sessionMapper).toDto(session);
    }

    @Test
    void update_invalidId() {
        ResponseEntity<?> response = sessionController.update("invalid", sessionDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).update(any(), any());
    }

    @Test
    void save() {
        when(sessionService.getById(1L)).thenReturn(session);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(sessionService).getById(1L);
        verify(sessionService).delete(1L);
    }

    @Test
    void save_invalidId() {
        ResponseEntity<?> response = sessionController.save("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).getById(any());
        verify(sessionService, never()).delete(any());
    }

    @Test
    void participate() {
        ResponseEntity<?> response = sessionController.participate("1", "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(sessionService).participate(1L, 1L);
    }

    @Test
    void participate_invalidId() {
        ResponseEntity<?> response = sessionController.participate("invalid", "1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).participate(any(), any());
    }

    @Test
    void noLongerParticipate() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(sessionService).noLongerParticipate(1L, 1L);
    }

    @Test
    void noLongerParticipate_invalidId() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("invalid", "1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).noLongerParticipate(any(), any());
    }

}
