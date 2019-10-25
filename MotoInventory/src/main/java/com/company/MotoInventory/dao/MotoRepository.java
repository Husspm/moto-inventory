package com.company.MotoInventory.dao;

import com.company.MotoInventory.repository.Motorcycle;

import java.util.List;

public interface MotoRepository {
    Motorcycle createMotorcycle(Motorcycle motorcycle);
    List<Motorcycle> getAllMotorcycle();
    Motorcycle getMotorcycle(int id);
    void updateMotorcycle(Motorcycle motorcycle);
    void deleteMotorcycle(int id);

}
