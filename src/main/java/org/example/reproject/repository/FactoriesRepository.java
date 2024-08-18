package org.example.reproject.repository;

import org.example.reproject.entity.Factories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoriesRepository extends JpaRepository<Factories, Integer> {

}
