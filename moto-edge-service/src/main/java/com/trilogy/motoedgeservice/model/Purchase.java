package com.trilogy.motoedgeservice.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase {

    private Motorcycle ordered;
    private BigDecimal salesTax;
    private BigDecimal documentFees;
    private BigDecimal transportationCost;
    private BigDecimal totalCost;

    public Motorcycle getOrdered() {
        return ordered;
    }

    public void setOrdered(Motorcycle ordered) {
        this.ordered = ordered;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public BigDecimal getDocumentFees() {
        return documentFees;
    }

    public void setDocumentFees(BigDecimal documentFees) {
        this.documentFees = documentFees;
    }

    public BigDecimal getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(BigDecimal transportationCost) {
        this.transportationCost = transportationCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return ordered.equals(purchase.ordered) &&
                salesTax.equals(purchase.salesTax) &&
                documentFees.equals(purchase.documentFees) &&
                transportationCost.equals(purchase.transportationCost) &&
                totalCost.equals(purchase.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordered, salesTax, documentFees, transportationCost, totalCost);
    }
}
