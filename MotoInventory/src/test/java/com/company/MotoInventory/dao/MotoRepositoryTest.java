package com.company.MotoInventory.dao;

import com.company.MotoInventory.repository.Motorcycle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MotoRepositoryTest {

    @Autowired
    private MotoRepository motoRepo;

    private Motorcycle moto;

    @Before
    public void setUp() throws Exception {
        motoRepo.getAllMotorcycle().forEach(m -> {
            motoRepo.deleteMotorcycle(m.getId());
        });

    }

    @Test
    public void createGetDeleteMotorcycle() {
        Motorcycle moto = new Motorcycle();
        moto.setId(1);
        moto.setPrice(new BigDecimal("15.99"));
        moto.setVin("somethingVIN");
        moto.setMake("somethingMAKE");
        moto.setModel("somethingMODEL");
        moto.setYear("2015");
        moto.setColor("Blue");
        motoRepo.createMotorcycle(moto);

        Motorcycle expected = motoRepo.getMotorcycle(moto.getId());
        assertEquals(moto, expected);

        motoRepo.deleteMotorcycle(moto.getId());
        Motorcycle fromDao = motoRepo.getMotorcycle(moto.getId());
        assertNull(fromDao);
    }

    @Test
    public void getAllMotorcycles() {
        Motorcycle moto = new Motorcycle();
        moto.setId(1);
        moto.setPrice(new BigDecimal("15.99"));
        moto.setVin("somethingVIN");
        moto.setMake("somethingMAKE");
        moto.setModel("somethingMODEL");
        moto.setYear("2015");
        moto.setColor("Blue");
        motoRepo.createMotorcycle(moto);
        Motorcycle expected = motoRepo.getMotorcycle(moto.getId());

        List<Motorcycle> mList = motoRepo.getAllMotorcycle();

        assertEquals(mList.size(),1);


    }

    @Test
    public void updateMotorcycle() {
        Motorcycle moto = new Motorcycle();
        moto.setId(1);
        moto.setPrice(new BigDecimal("15.99"));
        moto.setVin("somethingVIN");
        moto.setMake("somethingMAKE");
        moto.setModel("somethingMODEL");
        moto.setYear("2015");
        moto.setColor("Blue");
        motoRepo.createMotorcycle(moto);
        moto.setYear("10");
        Motorcycle expected = motoRepo.getMotorcycle(moto.getId());

        assertNotEquals(moto, expected);
    }
}