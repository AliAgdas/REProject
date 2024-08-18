package org.example.reproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Materials")
@Data
public class Materials implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="material_id")
    private int id;

    @Column(name="material_name")
    private String name;

    @Column(name="recycle_purchase_cost_usd")
    private double recyclePurchaseCostUsd;

    @Column(name="purchase_cost_usd")
    private double purchaseCostUsd;

    @ManyToMany(mappedBy = "materials_factories")
    @JsonIgnore
    private List<Factories> factoriesList;
}





