package com.trilogy.motoedgeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogy.motoedgeservice.model.Motorcycle;
import com.trilogy.motoedgeservice.model.Purchase;
import com.trilogy.motoedgeservice.service.MotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class MotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotoService service;

    private <T> String writeToJson(T obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }


    @Test
    public void test_getAllMotorcycles_WillReturnAnArrayOfMotorcycleObject_AndStatusIs_200() throws Exception {
        List<Motorcycle> allBikes = new ArrayList<>();
        Motorcycle bikeOne = new Motorcycle();
        bikeOne.setPrice(BigDecimal.valueOf(4000.99));
        bikeOne.setMake("Honda");
        bikeOne.setModel("CG150");
        bikeOne.setColor("Blue");
        bikeOne.setId(1l);
        bikeOne.setYear("1999");

        Motorcycle bikeTwo = new Motorcycle();
        bikeTwo.setPrice(BigDecimal.valueOf(9000.99));
        bikeTwo.setMake("Honda");
        bikeTwo.setModel("CB550");
        bikeTwo.setColor("Gold");
        bikeTwo.setId(2l);
        bikeTwo.setYear("2009");


        allBikes.add(bikeOne);
        allBikes.add(bikeTwo);

        when(service.getAll()).thenReturn(allBikes);

        mockMvc.perform(get("/motorcycles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(writeToJson(allBikes)));
    }

    @Test
    public void makeAPurchase() throws Exception {

        Motorcycle bikeOne = new Motorcycle();
        bikeOne.setPrice(BigDecimal.valueOf(4000.99));
        bikeOne.setVin("12345");
        bikeOne.setMake("Honda");
        bikeOne.setModel("CG150");
        bikeOne.setColor("Blue");
        bikeOne.setId(1l);
        bikeOne.setYear("1999");

        Purchase purchase = new Purchase();
        purchase.setOrdered(bikeOne);
        purchase.setSalesTax(BigDecimal.valueOf(0.0675));
        purchase.setDocumentFees(BigDecimal.valueOf(234.00).setScale(2));
        purchase.setTransportationCost(BigDecimal.valueOf(395.00).setScale(2));
        BigDecimal calculation = bikeOne.getPrice();
        calculation = calculation.add(BigDecimal.valueOf(234.00));
        calculation = calculation.add(BigDecimal.valueOf(395.00));
        calculation = calculation.add(bikeOne.getPrice().multiply(BigDecimal.valueOf(0.0675)));
        calculation = calculation.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        purchase.setTotalCost(calculation);
        when(service.makeAPurchase(bikeOne)).thenReturn(purchase);

        mockMvc.perform(post("/motorcycles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(writeToJson(bikeOne)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(writeToJson(purchase)));
    }
}