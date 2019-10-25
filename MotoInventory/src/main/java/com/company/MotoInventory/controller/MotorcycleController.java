package com.company.MotoInventory.controller;

import com.company.MotoInventory.dao.MotoRepository;
import com.company.MotoInventory.repository.Motorcycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorcycle")
public class MotorcycleController {

    @Autowired
    MotoRepository repo;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Motorcycle createMotorcycle(Motorcycle motorcycle) {
        Motorcycle motorcycle1 = repo.createMotorcycle(motorcycle);
        return motorcycle1;
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Motorcycle getMotorcycleById(int id) {
        Motorcycle motorcycle1 = repo.getMotorcycle(id);
        return motorcycle1;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<Motorcycle> getAllMotorcycles(){
        List<Motorcycle> mList = repo.getAllMotorcycle();
        return mList;
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMotorcycle(@RequestBody Motorcycle motorcycle, @PathVariable int id) {
        repo.updateMotorcycle(motorcycle);
    }

    @DeleteMapping("/{id}")
    public void deleteMotorcycle(@PathVariable int id) {
        repo.deleteMotorcycle(id);
    }
}
