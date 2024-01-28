package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportItem {
    private String identifier;
    private float  grossSales;
    private float netSales;
    private int orderCount;



    public ReportItem(String identifier) {
        this.identifier = identifier;
        this.orderCount=0;
    }

    public ReportItem(String identifier, float grossSales, float netSales) {
        this.identifier = identifier;
        this.grossSales = grossSales;
        this.netSales = netSales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportItem item = (ReportItem) o;
        return identifier.equals(item.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    public void addGrossSales(float amount) {
        this.grossSales+=amount;
    }
    public void addNetSales(float amount) {
        this.netSales+=amount;
    }

    public void increaseOrderCount() {

        this.orderCount++;
    }
}
