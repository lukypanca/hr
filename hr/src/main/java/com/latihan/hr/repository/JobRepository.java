package com.latihan.hr.repository;

import com.latihan.hr.entity.Job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String>{
    
}
