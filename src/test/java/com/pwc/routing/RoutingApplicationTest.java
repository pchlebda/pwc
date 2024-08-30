package com.pwc.routing;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RoutingApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetRoute_Success() throws Exception {
        mockMvc.perform(get("/routing/CZE/ITA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"route\":[\"CZE\",\"AUT\",\"ITA\"]}"));
    }

    @Test
    void testGetRoute_Lower_Case_Success() throws Exception {
        mockMvc.perform(get("/routing/czE/Aut")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"route\":[\"CZE\",\"AUT\"]}"));
    }

    @Test
    void testGetRoute_ValidationError() throws Exception {
        // Perform the GET request and expect a 400 Bad Request response with the error message
        mockMvc.perform(get("/routing/XYZ/ITA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Origin does not exist")));
    }

    @Test
    void testGetRoute_NoRouteFound() throws Exception {
        mockMvc.perform(get("/routing/CZE/USA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Route does not exist")));
    }
}
