package com.trilogy.motoedgeservice.util.feign;

import com.trilogy.motoedgeservice.model.Motorcycle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "moto-inventory")
public interface InventoryClient {
    @GetMapping("/motorcycle")
    public List<Motorcycle>getAll();
    @GetMapping("motorcycle/{id}")
    public Motorcycle findOne(@PathVariable Integer id);
}
