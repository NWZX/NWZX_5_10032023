package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeacherControllerTest {
    private TeacherService teacherService;
    private TeacherMapper teacherMapper;
    private TeacherController teacherController;

    private Teacher teacher;
    private TeacherDto teacherDto;
    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        teacherService = mock(TeacherService.class);
        teacherMapper = mock(TeacherMapper.class);
        teacherController = new TeacherController(teacherService, teacherMapper);

        teacher = new Teacher()
                .setId(1L)
                .setLastName("Doe")
                .setFirstName("John");

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("Doe");
        teacherDto.setFirstName("John");

        teachers = Arrays.asList(teacher);
    }

    @Test
    void findById() {
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherDto, response.getBody());

        verify(teacherService).findById(1L);
        verify(teacherMapper).toDto(teacher);
    }

    @Test
    void findById_invalidId() {
        ResponseEntity<?> response = teacherController.findById("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teacherService, never()).findById(any());
    }

    @Test
    void findAll() {
        when(teacherService.findAll()).thenReturn(teachers);
        when(teacherMapper.toDto(teachers)).thenReturn(Arrays.asList(teacherDto));

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(teacherDto), response.getBody());

        verify(teacherService).findAll();
        verify(teacherMapper).toDto(teachers);
    }
}
