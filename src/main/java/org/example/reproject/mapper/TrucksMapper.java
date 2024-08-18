package org.example.reproject.mapper;

import org.example.reproject.entity.Trucks;
import org.example.reproject.dto.TrucksDto;
import org.springframework.stereotype.Component;

@Component
public class TrucksMapper {

    public static TrucksDto toDto(Trucks trucks) {
        TrucksDto dto = new TrucksDto();
        dto.setId(trucks.getId());
        dto.setMaxCapacityKg(trucks.getMaxCapacityKg());
        dto.setIsFull(trucks.isFull());
        return dto;
    }

    public static Trucks toEntity(TrucksDto dto) {
        Trucks trucks = new Trucks();
        trucks.setId(dto.getId());
        trucks.setMaxCapacityKg(dto.getMaxCapacityKg());
        trucks.setFull(dto.getIsFull());
        return trucks;
    }

}
