package org.example.reproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Factories")
@Data
public class Factories implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="factory_id")
    private int id;

    @Column(name ="distance_km")
    private double distanceKm;

    @Column(name = "now_recycle_kg")
    private Double nowRecycleUsd;

    @OneToOne
    @JoinColumn(name = "truck_id")
    private Trucks truck;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Companies company_factories;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Countries country_factories;

    @ManyToMany
    @JoinTable(
            name = "factories_materials",
            joinColumns = @JoinColumn(name = "factory_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Materials> materials_factories;
}



