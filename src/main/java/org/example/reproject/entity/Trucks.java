package org.example.reproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "Trucks")
@Data
public class Trucks implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "truck_id")
    private int id;

    @Column(name="max_capacity_kg")
    private double maxCapacityKg;

    @Column(name="is_full")
    private boolean isFull;

    @OneToOne(mappedBy = "truck", cascade = CascadeType.ALL)
    @JsonIgnore
    private Factories factory;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Countries country_trucks;
}



