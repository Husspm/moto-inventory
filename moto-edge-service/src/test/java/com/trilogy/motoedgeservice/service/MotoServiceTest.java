package com.trilogy.motoedgeservice.service;

import com.trilogy.motoedgeservice.model.Motorcycle;
import com.trilogy.motoedgeservice.model.Purchase;
import com.trilogy.motoedgeservice.util.feign.InventoryClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class MotoServiceTest {

    @InjectMocks
    private MotoService service;

    @Mock
    private InventoryClient client;

    private Motorcycle motorcycle;
    private BigDecimal calculationMock;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        setupInventoryClientMock();
    }

    private void setupInventoryClientMock() {
        List<Motorcycle> allBikes = new ArrayList<>();
        motorcycle = new Motorcycle();
        motorcycle.setColor("Blue");
        motorcycle.setId(1l);
        motorcycle.setMake("Honda");
        motorcycle.setModel("BD260");
        motorcycle.setVin("12345");
        motorcycle.setYear("1999");
        motorcycle.setPrice(BigDecimal.valueOf(4000.99));
        calculationMock = motorcycle.getPrice();
        calculationMock = calculationMock.add(BigDecimal.valueOf(234.00));
        calculationMock = calculationMock.add(BigDecimal.valueOf(395.00));
        calculationMock = calculationMock.add(motorcycle.getPrice().multiply(BigDecimal.valueOf(0.0675)));
        calculationMock = calculationMock.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        allBikes.add(motorcycle);
        doReturn(motorcycle).when(client).findOne(1);
        doReturn(allBikes).when(client).getAll();
    }

    @Test
    public void test_getAll_WillReturnListOfAllMotorcycles() {
        List<Motorcycle> all = service.getAll();
        assertEquals(1, all.size());
    }

    @Test
    public void test_findOne_WillReturnMotorcycleObjectFromClient() {
        Motorcycle test = client.findOne(1);
        assertEquals(motorcycle, test);
    }

    @Test
    public void test_makeAPurchase_WillPerformCorrectCalulations() {

        Purchase purchase = service.makeAPurchase(motorcycle);
        assertEquals(BigDecimal.valueOf(234.00), purchase.getDocumentFees());
        assertEquals(motorcycle, purchase.getOrdered());
        assertEquals(BigDecimal.valueOf(395.00), purchase.getTransportationCost());
        assertEquals(BigDecimal.valueOf(0.0675), purchase.getSalesTax());
        assertEquals(calculationMock, purchase.getTotalCost());

    }
}