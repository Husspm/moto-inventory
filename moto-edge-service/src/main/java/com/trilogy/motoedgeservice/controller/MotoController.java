package com.trilogy.motoedgeservice.controller;

import com.trilogy.motoedgeservice.model.Motorcycle;
import com.trilogy.motoedgeservice.model.Purchase;
import com.trilogy.motoedgeservice.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MotoController {

    @Autowired
    MotoService service;

    @GetMapping("/motorcycles")
    @ResponseStatus(HttpStatus.OK)
    public List<Motorcycle> getAllMotorcycles() {
        return service.getAll();
    }

    @PostMapping("/motorcycles")
    @ResponseStatus(HttpStatus.OK)
    public Purchase makeAPurchase(Motorcycle motorcycle) {
        return service.makeAPurchase(motorcycle);
    }
}
