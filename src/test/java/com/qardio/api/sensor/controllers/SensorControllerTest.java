package com.qardio.api.sensor.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qardio.api.sensor.models.SensorLogHourlyAggregated;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Sensor controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * List should have result.
     *
     * @throws Exception the exception
     */
    @Test
    void listShouldHaveResult() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/log")
                        .contentType("application/json")
                        .content("{\"aggregationType\":\"hourly\"}")
        )
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<SensorLogHourlyAggregated> list =
                objectMapper.readValue(json, new TypeReference<List<SensorLogHourlyAggregated>>() {
                });

        assertTrue(list.size() > 0);
    }

    /**
     * List should have result default aggregation type.
     *
     * @throws Exception the exception
     */
    @Test
    void listShouldHaveResultDefaultAggregationType() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/log")
                        .contentType("application/json")
                        .content("{\"invalid\":\"invalid\"}")
        )
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<SensorLogHourlyAggregated> list =
                objectMapper.readValue(json, new TypeReference<List<SensorLogHourlyAggregated>>() {
                });

        assertTrue(list.size() > 0);
    }

    /**
     * List should fail invalid aggregation type.
     *
     * @throws Exception the exception
     */
    @Test
    void listShouldFailInvalidAggregationType() throws Exception {
        this.mockMvc.perform(
                get("/log")
                        .contentType("application/json")
                        .content("{\"aggregationType\":\"yearly\"}")
        )
                .andExpect(status().isBadRequest());
    }

    /**
     * Save.
     *
     * @throws Exception the exception
     */
    @Test
    void save() throws Exception {

        MvcResult result = this.mockMvc.perform(
                post("/log")
                        .contentType("application/json")
                        .content("[{\"temperature\":30.5,\"when\":\"2021-08-27T18:00:00.000\"},{\"temperature\":50.5,\"when\":\"2021-08-20T18:16:00.000\"}]")
        )
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Map<String, Object> map
                = this.objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

        assertEquals(2, map.get("logsToSave"));
        assertEquals(2, map.get("logsSaved"));
        assertEquals(true, map.get("aggregationGenerated"));
    }

    /**
     * Save should fail due invalid payload.
     *
     * @throws Exception the exception
     */
    @Test
    void saveShouldFailDueInvalidPayload() throws Exception {

        MvcResult result = this.mockMvc.perform(
                post("/log")
                        .contentType("application/json")
                        .content("[{\"temperature\":30.5,\"when\":\"ASD\"},{\"temperature\":50.5,\"when\":\"2021-08-20T18:16:00.000\"}]")
        )
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}