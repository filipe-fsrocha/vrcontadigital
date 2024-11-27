package br.com.fsrocha.vrcontadigital;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(classes = VrcontadigitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class UnitTest {

    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    DataSource dataSource;

    @Autowired
    Flyway flyway;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP ALL OBJECTS");
                stmt.execute("CREATE SCHEMA test");
                stmt.execute("SET SCHEMA test");
            }
            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    protected String mapToJson(Object object) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    protected <T> T mapToDto(String json, Class<T> tClass) throws IOException {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, tClass);
    }

}
