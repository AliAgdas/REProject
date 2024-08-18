package org.example.reproject.repository;

import org.example.reproject.entity.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends JpaRepository<Countries, Integer> {

}
