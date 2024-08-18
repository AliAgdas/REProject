package org.example.reproject.repository;

import org.example.reproject.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Integer> {

}
