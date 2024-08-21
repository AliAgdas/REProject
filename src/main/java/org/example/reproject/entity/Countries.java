package org.example.reproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Countries")
@Data
public class Countries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    @Column(name="country_name")
    private String name;

    @Column(name="recycle_bonus_usd")
    private double recycleBonusUsd;

    @Column (name ="min_kg_for_bonus")
    private double minKgForBonus;

    @OneToMany(mappedBy = "country_factories")
    @JsonIgnore
    private List<Factories> factoriesList;

    @OneToMany(mappedBy = "country_trucks")
    @JsonIgnore
    private List<Trucks> truckList;

    @ManyToMany(mappedBy = "companies_countries")
    @JsonIgnore
    private List<Companies> companiesList;
}

