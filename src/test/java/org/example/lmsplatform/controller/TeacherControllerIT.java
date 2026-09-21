package org.example.lmsplatform.controller;

import org.example.lmsplatform.IntegrationTest;
import org.example.lmsplatform.dao.TeacherRepository;
import org.example.lmsplatform.model.dto.TeacherDtoRequest;
import org.example.lmsplatform.model.dto.TeacherDtoResponse;
import org.example.lmsplatform.model.entity.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


public class TeacherControllerIT extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TeacherRepository teacherRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        teacherRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void shouldCreateTeacher() {
        TeacherDtoRequest request = new TeacherDtoRequest("Иван", "Иванов");
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> response = restTemplate.postForEntity(
                url,
                request,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        TeacherDtoResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Иван", body.name());
        assertEquals("Иванов", body.surname());
        assertTrue(teacherRepository.existsById(body.id()));
        assertEquals(1, teacherRepository.count());
    }

    @Test
    void shouldReturnExceptionWhenNameIsBlank() {
        TeacherDtoRequest request = new TeacherDtoRequest("  ", "Иванов");
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldReturnExceptionWhenSurnameIsBlank() {
        TeacherDtoRequest request = new TeacherDtoRequest("Иван", " ");
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldReturnExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(101);
        TeacherDtoRequest request = new TeacherDtoRequest(longName, "Иванов");
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldReturnExceptionWhenSurnameIsTooLong() {
        String longSurname = "A".repeat(101);
        TeacherDtoRequest request = new TeacherDtoRequest("Иван", longSurname);
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldReturnExceptionWhenNameIsNull() {
        TeacherDtoRequest request = new TeacherDtoRequest(null, "Иванов");
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldReturnExceptionWhenSurnameIsNull() {
        TeacherDtoRequest request = new TeacherDtoRequest("Иван", null);
        String url = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldGetTeacherById() {
        TeacherDtoRequest request = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                request,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());

        String getUrl = "http://localhost:" + port + "/api/v1/teachers/" + createdTeacher.id();
        ResponseEntity<TeacherDtoResponse> getResponse = restTemplate.getForEntity(getUrl, TeacherDtoResponse.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        TeacherDtoResponse foundedTeacher = getResponse.getBody();
        assertEquals(createdTeacher, foundedTeacher);
    }

    @Test
    void shouldReturnNotFoundTeacherById() {
        String getUrl = "http://localhost:" + port + "/api/v1/teachers/" + 9;
        ResponseEntity<TeacherDtoResponse> getResponse = restTemplate.getForEntity(getUrl, TeacherDtoResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldUpdateTeacher() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());


        TeacherDtoRequest updatedRequest = new TeacherDtoRequest("Петр", "Петров");


        String updateUrl = "http://localhost:" + port + "/api/v1/teachers/" + createdTeacher.id();

        ResponseEntity<TeacherDtoResponse> updateResponse = restTemplate.exchange(
                updateUrl, HttpMethod.PUT, new HttpEntity<>(updatedRequest), TeacherDtoResponse.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

        TeacherDtoResponse updatedTeacher = updateResponse.getBody();
        assertNotNull(updatedTeacher);

        assertEquals(createdTeacher.id(), updatedTeacher.id());
        assertEquals("Петр", updatedTeacher.name());
        assertEquals("Петров", updatedTeacher.surname());

        Teacher teacherFromDb = teacherRepository.findById(updatedTeacher.id())
                .orElseThrow();

        assertEquals("Петр", teacherFromDb.getName());
        assertEquals("Петров", teacherFromDb.getSurname());
    }

    @Test
    void shouldReturnExceptionWhenUpdatedTeacherWithNameIsNull() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());

        TeacherDtoRequest updatedRequest = new TeacherDtoRequest(null, "Петров");
        String updateUrl = "http://localhost:" + port + "/api/v1/teachers/" + createdTeacher.id();

        ResponseEntity<String> updateResponse = restTemplate.exchange(
                updateUrl, HttpMethod.PUT, new HttpEntity<>(updatedRequest), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, updateResponse.getStatusCode());

        Teacher teacher = teacherRepository.findById(createdTeacher.id()).orElseThrow();

        assertEquals("Иван", teacher.getName());
        assertEquals("Иванов", teacher.getSurname());
    }

    @Test
    void shouldReturnExceptionWhenUpdatedTeacherWithSurnameIsBlank() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());

        TeacherDtoRequest updatedRequest = new TeacherDtoRequest("Петр", " ");
        String updateUrl = "http://localhost:" + port + "/api/v1/teachers/" + createdTeacher.id();

        ResponseEntity<String> updateResponse = restTemplate.exchange(
                updateUrl, HttpMethod.PUT, new HttpEntity<>(updatedRequest), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, updateResponse.getStatusCode());

        Teacher teacher = teacherRepository.findById(createdTeacher.id()).orElseThrow();

        assertEquals("Иван", teacher.getName());
        assertEquals("Иванов", teacher.getSurname());
    }

    @Test
    void shouldReturnExceptionWhenUpdatedTeacherWithNameIsTooLong() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());

        String longName = "a".repeat(101);
        TeacherDtoRequest updatedRequest = new TeacherDtoRequest(longName, "Петров");
        String updateUrl = "http://localhost:" + port + "/api/v1/teachers/" + createdTeacher.id();

        ResponseEntity<String> updateResponse = restTemplate.exchange(
                updateUrl, HttpMethod.PUT, new HttpEntity<>(updatedRequest), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, updateResponse.getStatusCode());

        Teacher teacher = teacherRepository.findById(createdTeacher.id()).orElseThrow();

        assertEquals("Иван", teacher.getName());
        assertEquals("Иванов", teacher.getSurname());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingTeacher() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());

        TeacherDtoRequest updatedRequest = new TeacherDtoRequest("Петр", "Петров");
        String updateUrl = "http://localhost:" + port + "/api/v1/teachers/" + (createdTeacher.id() + 1);

        ResponseEntity<String> updateResponse = restTemplate.exchange(
                updateUrl, HttpMethod.PUT, new HttpEntity<>(updatedRequest), String.class);

        assertEquals(HttpStatus.NOT_FOUND, updateResponse.getStatusCode());

        Teacher teacher = teacherRepository
                .findById(createdTeacher.id())
                .orElseThrow();

        assertEquals("Иван", teacher.getName());
        assertEquals("Иванов", teacher.getSurname());
    }

    @Test
    void shouldDeleteTeacher() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertTrue(teacherRepository.existsById(createdTeacher.id()));
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());

        String deleteUrl = "http://localhost:" + port + "/api/v1/teachers/" + createdTeacher.id();

        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                deleteUrl,
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        assertFalse(teacherRepository.existsById(createdTeacher.id()));
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingTeacher() {
        TeacherDtoRequest createdRequest = new TeacherDtoRequest("Иван", "Иванов");
        String createUrl = "http://localhost:" + port + "/api/v1/teachers";

        ResponseEntity<TeacherDtoResponse> createResponse = restTemplate.postForEntity(
                createUrl,
                createdRequest,
                TeacherDtoResponse.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        TeacherDtoResponse createdTeacher = createResponse.getBody();
        assertNotNull(createdTeacher);
        assertNotNull(createdTeacher.id());
        assertTrue(teacherRepository.existsById(createdTeacher.id()));
        assertEquals("Иван", createdTeacher.name());
        assertEquals("Иванов", createdTeacher.surname());

        String deleteUrl = "http://localhost:" + port + "/api/v1/teachers/" + (createdTeacher.id() + 1);

        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                deleteUrl,
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
        assertTrue(teacherRepository.existsById(createdTeacher.id()));
        assertEquals(1, teacherRepository.count());
    }


}
