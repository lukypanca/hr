package com.latihan.hr.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.latihan.hr.entity.JobHistory;
import com.latihan.hr.repository.DepartmentRepository;
import com.latihan.hr.repository.JobHistoryRepository;
import com.latihan.hr.repository.JobRepository;
import com.latihan.hr.util.PaginationList;
import com.latihan.hr.wrapper.JobHistoryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobHistoryService {
    
    @Autowired
    JobHistoryRepository jobHistoryRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobRepository jobRepository;

    // TO WRAPPER
    private JobHistoryWrapper toWrapper(JobHistory entity) {
        JobHistoryWrapper wrapper = new JobHistoryWrapper();
        wrapper.setEmployeeId(entity.getEmployeeId());
        wrapper.setStartDate(entity.getStartDate());
        wrapper.setEndDate(entity.getEndDate());
        wrapper.setJobId(entity.getJob() != null ? entity.getJob().getJobId() : null);
        wrapper.setJobTitle(entity.getJob() != null ? entity.getJob().getJobTitle() : null);
        wrapper.setDepartmentId(entity.getDepartment() != null ? entity.getDepartment().getDepartmentId() : null);
        wrapper.setDepartmentName(entity.getDepartment() != null ? entity.getDepartment().getDepartmentName() : null);

        return wrapper;
    }

    private List<JobHistoryWrapper> toWrapperList(List<JobHistory> entityList) {
        List<JobHistoryWrapper> wrapperList = new ArrayList<JobHistoryWrapper>();
        for (JobHistory entity : entityList) {
            JobHistoryWrapper wrapper = toWrapper(entity);
            wrapperList.add(wrapper); 
        } 
        return wrapperList;
    }

    private PaginationList<JobHistoryWrapper, JobHistory> toPaginationList(Page<JobHistory> entityPage) {
        List<JobHistory> entityList = entityPage.getContent();
        List<JobHistoryWrapper> wrapperList = toWrapperList(entityList);
        PaginationList<JobHistoryWrapper, JobHistory> paginationList = new PaginationList<JobHistoryWrapper, JobHistory>(wrapperList, entityPage);
        return paginationList;
    }

    // GET BY EMPLOYEE ID
    public List<JobHistoryWrapper> getByEmployeeId(Long employeeId) {
        List<JobHistory> entityList = jobHistoryRepository.getByEmployeeId(employeeId);
        return toWrapperList(entityList);
    }

    // FIND ALL
    public List<JobHistoryWrapper> findAll() {
        List<JobHistory> entityList = jobHistoryRepository.findAll();
        return toWrapperList(entityList);
    }

    // FIND ALL WITH PAGINATION
    public PaginationList<JobHistoryWrapper, JobHistory> findAllWithPagination(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return toPaginationList(jobHistoryRepository.findAll(paging));
    }

}
