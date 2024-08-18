package org.example.reproject.repository;

import org.example.reproject.entity.Countries;
import org.example.reproject.entity.Trucks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrucksRepository extends JpaRepository<Trucks, Integer> {

    @Query("SELECT t FROM Trucks t WHERE t.isFull = false AND t.country_trucks = :country AND t.maxCapacityKg >= :capacity")
    List<Trucks> findByIsFullFalseAndCountry(@Param("country") Countries country, @Param("capacity") double capacity);
}
