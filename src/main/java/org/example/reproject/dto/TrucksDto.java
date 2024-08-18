package org.example.reproject.dto;

import lombok.Data;

@Data
public class TrucksDto {
    private int id;
    private double maxCapacityKg;
    private Boolean isFull;
}
