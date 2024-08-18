package org.example.reproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Companies")
@Data
public class Companies implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "total_recycle_earning_usd")
    private double totalRecycleEarningUsd;

    @OneToMany(mappedBy = "company_factories")
    @JsonIgnore
    private List<Factories> factoriesList;

    @ManyToMany()
    @JoinTable(
            name = "companies_countries",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<Countries> companies_countries;
}



