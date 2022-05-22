package com.latihan.hr.repository;

import java.util.List;

import com.latihan.hr.entity.JobHistory;
import com.latihan.hr.entity.JobHistoryId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryId>{
    List<JobHistory> getByEmployeeId(Long employeeId);
}
