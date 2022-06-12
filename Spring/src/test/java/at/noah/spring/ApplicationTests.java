package at.noah.spring;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class Saving {

        @Test
        @DirtiesContext
            // nur beim Verändern von Daten anzuwenden
        void works() throws Exception {
            var json = """
                    {
                       "id": "NEU",
                       "firstName": "Neu",
                       "lastName": "Neu"
                    }
                    """;
            var resource = "/api/employees/NEU";

            mockMvc.perform(post("/api/employees")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost" + resource))
                    .andExpect(content().json(json)).andReturn();
            mockMvc.perform(get(resource))
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
        }
    }
}
