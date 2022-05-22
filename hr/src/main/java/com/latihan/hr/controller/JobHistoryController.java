package com.latihan.hr.controller;

import com.latihan.hr.entity.JobHistory;
import com.latihan.hr.service.JobHistoryService;
import com.latihan.hr.util.DataResponseList;
import com.latihan.hr.util.DataResponsePagination;
import com.latihan.hr.wrapper.JobHistoryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "jobHistory")
public class JobHistoryController {
    
    @Autowired
    JobHistoryService jobHistoryService;

    @GetMapping(path = "/findAll")
    DataResponseList<JobHistoryWrapper> findAll() {
        return new DataResponseList<JobHistoryWrapper>(jobHistoryService.findAll());
    }

    @GetMapping(path = "/findAllWithPagination")
    DataResponsePagination<JobHistoryWrapper, JobHistory> findAllWithPagination(@RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<JobHistoryWrapper, JobHistory>(jobHistoryService.findAllWithPagination(page, size));
    }

    @GetMapping(path = "/getById")
    DataResponseList<JobHistoryWrapper> getById(@RequestParam Long employeeId) {
        return new DataResponseList<JobHistoryWrapper>(jobHistoryService.getByEmployeeId(employeeId));
    }

}
