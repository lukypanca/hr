package com.latihan.hr.repository;

import com.latihan.hr.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
//    Page<Region> findAllWithPagination(Pageable page);
    Page<Region> findByRegionNameContainingIgnoreCase(String regionName, Pageable page);
}
