package com.latihan.hr.repository;

import com.latihan.hr.entity.Country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String>{

    Page<Country> findByCountryNameContainingIgnoreCase(String countryName, Pageable page);
}
