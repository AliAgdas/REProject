package org.example.reproject.repository;

import org.example.reproject.entity.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Integer> {

}
