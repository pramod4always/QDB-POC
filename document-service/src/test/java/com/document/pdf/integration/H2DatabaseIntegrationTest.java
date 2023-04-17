package com.document.pdf.integration;

import com.document.pdf.entity.DocumentInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@TestPropertySource("/application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class H2DatabaseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    String baseUrl = "http://localhost:";
    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
        jdbcTemplate.execute("insert into Student(name, grade) values('Jay', 7)");
    }

    @After
    void emptyData(){
        Long totalRecords = jdbcTemplate.queryForObject("Select count(*) from documents", Long.class);
        jdbcTemplate.execute("DELETE FROM documents");
    }

    @Test
    @DisplayName("Test Get Document")
    @Sql(scripts = "classpath:/data.sql")
    void testGetDocument() {
        ResponseEntity<DocumentInfo> documents = restTemplate.getForEntity((baseUrl + "/view-all"), DocumentInfo.class);
        assertAll(
                () -> assertNotNull(documents.getBody()),
                () -> assertNotNull(documents.getBody().getDocumentName()),
                () -> assertEquals(documents.getBody().getDocumentName(), "DOCUMENT_01.pdf")
        );
    }

}
