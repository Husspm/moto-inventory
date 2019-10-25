package com.trilogy.motoedgeservice.service;

import com.trilogy.motoedgeservice.model.Motorcycle;
import com.trilogy.motoedgeservice.model.Purchase;
import com.trilogy.motoedgeservice.util.feign.InventoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MotoService {
    @Autowired
    private InventoryClient client;

    public List<Motorcycle> getAll() {
//        return null;
        return client.getAll();
    }

    public Motorcycle findOne(Integer id) {
        return client.findOne(id);
    }

    public Purchase makeAPurchase(Motorcycle motorcycle) {

        BigDecimal tax = BigDecimal.valueOf(0.0675);
        BigDecimal fee = BigDecimal.valueOf(234.00);

        BigDecimal transportationCost;
        if (motorcycle.getPrice().compareTo(BigDecimal.valueOf(9999)) == -1) {
            transportationCost = BigDecimal.valueOf(395.00);
        } else {
            transportationCost = BigDecimal.valueOf(499.00);
        }

        BigDecimal originalCost = motorcycle.getPrice();

        BigDecimal withTax = motorcycle.getPrice().multiply(tax);
        motorcycle.setPrice(motorcycle.getPrice().add(fee));
        motorcycle.setPrice(motorcycle.getPrice().add(transportationCost));
        motorcycle.setPrice(motorcycle.getPrice().add(withTax));
        motorcycle.setPrice(motorcycle.getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN));

        Purchase purchase = new Purchase();
        purchase.setOrdered(motorcycle);
        purchase.setDocumentFees(fee);
        purchase.setSalesTax(tax);
        purchase.setTransportationCost(transportationCost);
        purchase.setTotalCost(motorcycle.getPrice());

        purchase.getOrdered().setPrice(originalCost);

        return purchase;

    }

}
