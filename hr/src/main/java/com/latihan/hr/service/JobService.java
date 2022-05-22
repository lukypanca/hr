package com.latihan.hr.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.latihan.hr.entity.Job;
import com.latihan.hr.repository.JobRepository;
import com.latihan.hr.util.PaginationList;
import com.latihan.hr.wrapper.JobWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobService {
    
    @Autowired
    JobRepository jobRepository;

    // TO ENTITY
    private Job toEntity(JobWrapper wrapper) {
        Job entity = new Job();
        entity.setJobId(wrapper.getJobId());
        entity.setJobTitle(wrapper.getJobTitle());
        entity.setMinSalary(wrapper.getMinSalary());
        entity.setMaxSalary(wrapper.getMaxSalary());
        
        return entity;
    }

    // TO WRAPPER
    private JobWrapper toWrapper(Job entity) {
        JobWrapper wrapper = new JobWrapper();
        wrapper.setJobId(entity.getJobId());
        wrapper.setJobTitle(entity.getJobTitle());
        wrapper.setMinSalary(entity.getMinSalary());
        wrapper.setMaxSalary(entity.getMaxSalary());

        return wrapper;
    }

    // TO WRAPPER LIST
    private List<JobWrapper> toWrapperList(List<Job> entityList) {
        List<JobWrapper> wrapperList = new ArrayList<JobWrapper>();
        for (Job entity : entityList) {
            JobWrapper jobWrapper = toWrapper(entity);
            wrapperList.add(jobWrapper);
        }

        return wrapperList;
    }

    // TO PAGINATION LIST
    private PaginationList<JobWrapper, Job> toPaginationList(Page<Job> entityPage) {
        List<Job> entityList = entityPage.getContent();
        List<JobWrapper> wrapperList = toWrapperList(entityList);
        PaginationList<JobWrapper, Job> paginationList = new PaginationList<JobWrapper, Job>(wrapperList, entityPage);
        return paginationList;
    }

    // FIND ALL
    public List<JobWrapper> findAll() {
        List<Job> jobList = jobRepository.findAll();
        return toWrapperList(jobList); 
    }

    // FIND ALL WITH PAGINATION
    public PaginationList<JobWrapper, Job> findAllWithPagination(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return toPaginationList(jobRepository.findAll(paging));
    }

    // GET BY ID
    public JobWrapper getById(String id) {
        Job job = jobRepository.getById(id);
        return toWrapper(job);
    }

    // SAVE
    public JobWrapper save(JobWrapper wrapper) {
        Job job = jobRepository.save(toEntity(wrapper));
        return toWrapper(job);
    }

    // DELETE
    public void delete(String id) {
        jobRepository.deleteById(id);
    }

}
