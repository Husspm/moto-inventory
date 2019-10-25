package com.company.MotoInventory.controller;

import com.company.MotoInventory.dao.MotoRepository;
import com.company.MotoInventory.repository.Motorcycle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MotorcycleController.class)
public class MotorcycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MotorcycleController controller;

    @MockBean
    private MotoRepository motoRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldCreateMotorcycle() {
        Motorcycle input = new Motorcycle();
        input.setPrice(new BigDecimal("15.99"));
        input.setVin("somethingVIN");
        input.setMake("somethingMAKE");
        input.setModel("somethingMODEL");
        input.setYear("2015");
        input.setColor("Blue");

        String inputJson;
        try {
            inputJson = mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        Motorcycle output = new Motorcycle();
        output.setId(1);
        output.setPrice(new BigDecimal("15.99"));
        output.setVin("somethingVIN");
        output.setMake("somethingMAKE");
        output.setModel("somethingMODEL");
        output.setYear("2015");
        output.setColor("Blue");

        String outputJson;
        try {
            outputJson = mapper.writeValueAsString(output);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert to JSON");
        }

        when(motoRepository.createMotorcycle(input)).thenReturn(output);
        try {
            this.mockMvc.perform(post("/motorcycle")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(content().json(outputJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetMotorcycleById() throws Exception {
        Motorcycle output = new Motorcycle();
        output.setId(1);
        output.setPrice(new BigDecimal("15.99"));
        output.setVin("somethingVIN");
        output.setMake("somethingMAKE");
        output.setModel("somethingMODEL");
        output.setYear("2015");
        output.setColor("Blue");


        String outputJson;
        outputJson = mapper.writeValueAsString(output);

        when(motoRepository.getMotorcycle(1)).thenReturn(output);

        this.mockMvc.perform(get("/motorcycle/" + output.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldGetAllMotorcycles() throws Exception {
        Motorcycle output = new Motorcycle();
        output.setId(1);
        output.setPrice(new BigDecimal("15.99"));
        output.setVin("somethingVIN");
        output.setMake("somethingMAKE");
        output.setModel("somethingMODEL");
        output.setYear("2015");
        output.setColor("Blue");

        List<Motorcycle> mList = new ArrayList<>();
        mList.add(output);

        String outputJson;
        outputJson = mapper.writeValueAsString(mList);

        when(motoRepository.getAllMotorcycle()).thenReturn(mList);

        this.mockMvc.perform(get("/motorcycle"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(outputJson));
    }

    @Test
    public void updateMotorcycle() throws Exception {
        Motorcycle output = new Motorcycle();
        output.setId(1);
        output.setPrice(new BigDecimal("15.99"));
        output.setVin("somethingVIN");
        output.setMake("somethingMAKE");
        output.setModel("somethingMODEL");
        output.setYear("2015");
        output.setColor("Blue");

        String inputJson;
        inputJson = mapper.writeValueAsString(output);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/motorcycle/" + output.getId())
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
    }

    @Test
    public void shouldDeleteMotorcycle() throws Exception {
        controller.deleteMotorcycle(1);

        verify(motoRepository, times(1)).deleteMotorcycle(1);

        this.mockMvc.perform(delete("/motorcycle/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
    }


}