package com.latihan.hr.repository;

import com.latihan.hr.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
}
