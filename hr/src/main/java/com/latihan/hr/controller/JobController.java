package com.latihan.hr.controller;

import com.latihan.hr.entity.Job;
import com.latihan.hr.service.JobService;
import com.latihan.hr.util.DataResponse;
import com.latihan.hr.util.DataResponseList;
import com.latihan.hr.util.DataResponsePagination;
import com.latihan.hr.wrapper.JobWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "job")
public class JobController {
    
    @Autowired
    JobService jobService;

    // FIND ALL
    @GetMapping(path = "/findAll")
    DataResponseList<JobWrapper> findAll() {
        return new DataResponseList<JobWrapper>(jobService.findAll());
    }

    // FIND ALL WITH PAGINATION
    @GetMapping(path = "findAllWithPagination")
    DataResponsePagination<JobWrapper, Job> findAllWithPagination(@RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<JobWrapper, Job>(jobService.findAllWithPagination(page, size));
    }

    // GET BY ID
    @GetMapping(path = "/getById")
    DataResponse<JobWrapper> getById(@RequestParam String jobId) {
        try {
            return new DataResponse<JobWrapper>(jobService.getById(jobId));
        } catch (Exception e) {
            return new DataResponse<JobWrapper>(false, "job dengan id" + jobId + "tidak ditemukan");
        }
        
    }

    // CREATE
    @PostMapping(path = "/post")
    DataResponse<JobWrapper> create(@RequestBody JobWrapper createWrapper) {
        return new DataResponse<JobWrapper>(jobService.save(createWrapper));
    }

    // UPDATE
    @PutMapping(path = "/put")
    DataResponse<JobWrapper> update(@RequestBody JobWrapper updateWrapper) {
        try {
            return new DataResponse<JobWrapper>(jobService.save(updateWrapper));
        } catch (Exception e) {
            return new DataResponse<JobWrapper>(false, "job dengan id " + updateWrapper.getJobId() + " tidak ditemukan");
        }
    }

    // DELETE
    @DeleteMapping(path = "/delete")
    DataResponse<JobWrapper> delete(@RequestParam String jobId) {
        try {
            jobService.delete(jobId);
            return new DataResponse<JobWrapper>(true, "delete berhasil");
        } catch (Exception e) {
            return new DataResponse<JobWrapper>(false, "Job dengan id " + jobId + " tidak ditemukan"); 
        }
    }

}
