package com.latihan.hr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.latihan.hr.entity.Department;
import com.latihan.hr.entity.Employee;
import com.latihan.hr.entity.Job;
import com.latihan.hr.entity.JobHistory;
import com.latihan.hr.exception.BusinessException;
import com.latihan.hr.repository.DepartmentRepository;
import com.latihan.hr.repository.EmployeeRepository;
import com.latihan.hr.repository.JobHistoryRepository;
import com.latihan.hr.repository.JobRepository;
import com.latihan.hr.util.PaginationList;
import com.latihan.hr.wrapper.EmployeeWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobHistoryRepository jobHistoryRepository;

    // TO ENTITY
    private Employee toEntity(EmployeeWrapper wrapper) {
        Employee entity = new Employee();
        if (wrapper.getEmployeeId() != null) {
            entity = employeeRepository.getById(wrapper.getEmployeeId());
        }
        entity.setFirstName(wrapper.getFirstName());
        entity.setLastName(wrapper.getLastName());
        entity.setEmail(wrapper.getEmail());
        entity.setPhoneNumber(wrapper.getPhoneNumber());
        entity.setHireDate(wrapper.getHireDate());
        Optional<Job> optionalJob = jobRepository.findById(wrapper.getJobId());
        Job job = optionalJob.isPresent() ? optionalJob.get() : null;
        entity.setJob(job);
        entity.setSalary(wrapper.getSalary());
        entity.setCommissionPct(wrapper.getCommissionPct());
        Optional<Employee> optionalManager = employeeRepository.findById(wrapper.getManagerId());
        Employee manager = optionalManager.isPresent() ? optionalManager.get() : null;
        entity.setManager(manager);
        Optional<Department> optionalDepartment = departmentRepository.findById(wrapper.getDepartmentId());
        Department department = optionalDepartment.isPresent() ? optionalDepartment.get() : null;
        entity.setDepartment(department);

        return entity;
    }

    // TO WRAPPER
    private EmployeeWrapper toWrapper(Employee entity) {
        EmployeeWrapper wrapper = new EmployeeWrapper();
        wrapper.setEmployeeId(entity.getEmployeeId());
        wrapper.setFirstName(entity.getFirstName());
        wrapper.setLastName(entity.getLastName());
        wrapper.setFullName(entity.getFirstName() + ' ' + entity.getLastName());
        wrapper.setEmail(entity.getEmail());
        wrapper.setPhoneNumber(entity.getPhoneNumber());
        wrapper.setHireDate(entity.getHireDate());
        wrapper.setJobId(entity.getJob() != null ? entity.getJob().getJobId() : null);
        wrapper.setJobTitle(entity.getJob() != null ? entity.getJob().getJobTitle() : null);
        wrapper.setSalary(entity.getSalary());
        wrapper.setCommissionPct(entity.getCommissionPct());
        wrapper.setManagerId(entity.getManager() != null ? entity.getManager().getEmployeeId() : null );
        wrapper.setManagerName(entity.getManager() != null ? entity.getManager().getFirstName() + ' ' + entity.getManager().getLastName() : null);
        wrapper.setDepartmentId(entity.getDepartment() != null ? entity.getDepartment().getDepartmentId() : null);
        wrapper.setDepartmentName(entity.getDepartment() != null ? entity.getDepartment().getDepartmentName() : null);

        return wrapper;
    }

    // TO WRAPPER LIST
    private List<EmployeeWrapper> toWrapperList(List<Employee> entityList) {
        List<EmployeeWrapper> employeeList = new ArrayList<EmployeeWrapper>();
        for (Employee entity : entityList) {
            EmployeeWrapper employee = toWrapper(entity);
            employeeList.add(employee);
        }
        return employeeList;
    }

    // TO PAGINATION LIST
    private PaginationList<EmployeeWrapper, Employee> toPaginationList(Page<Employee> entityPage) {
        List<Employee> entityList = entityPage.getContent();
        List<EmployeeWrapper> wrapperList = toWrapperList(entityList);
        PaginationList<EmployeeWrapper, Employee> paginationList = new PaginationList<EmployeeWrapper, Employee>(wrapperList, entityPage);

        return paginationList;
    }
    
    // FIND ALL
    public List<EmployeeWrapper> findAll() {
        List<Employee> employee = employeeRepository.findAll();
        return toWrapperList(employee);
    }

    // FIND ALL WITH PAGINATION
    public PaginationList<EmployeeWrapper, Employee> findAllWithPaginationList(int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("employeeId").ascending());
        return toPaginationList(employeeRepository.findAll(paging));
    }

    // GET BY EMPLOYEE ID
    public EmployeeWrapper getById(Long id) {
        Employee employee = employeeRepository.getById(id);
        return toWrapper(employee);
    }

    // SAVE
    public EmployeeWrapper save(EmployeeWrapper wrapper) {
        Employee employee = new Employee();
        if(wrapper.getEmployeeId() == null) {
            if(wrapper.getSalary() < 0) {
                throw new BusinessException("Salary must be not negatif");
            }
            if(wrapper.getLastName() == null) {
                throw new BusinessException("Last Name can not be null");
            }
            employee = employeeRepository.save(toEntity(wrapper));
        } else {
            Optional<Employee> employeeExisted = employeeRepository.findById(wrapper.getEmployeeId());
            if(!employeeExisted.isPresent()) {
                throw new BusinessException("Employee with id " + wrapper.getEmployeeId() + " doesn't exist");
            } 
            String jobLama = employeeExisted.get().getJob().getJobId();
            Long departmentLama = employeeExisted.get().getDepartment().getDepartmentId();
            if(!jobLama.equals(wrapper.getJobId()) || !departmentLama.equals(wrapper.getDepartmentId())) {
                JobHistory jobHistory = new JobHistory();
                jobHistory.setEmployeeId(employeeExisted.get().getEmployeeId());
                jobHistory.setStartDate(employeeExisted.get().getHireDate());
                jobHistory.setEndDate(wrapper.getHireDate());
                jobHistory.setJob(employeeExisted.get().getJob());
                jobHistory.setDepartment(employeeExisted.get().getDepartment());
                jobHistoryRepository.save(jobHistory);
            }
            employee = employeeRepository.save(toEntity(wrapper));
        }
        return toWrapper(employee);
    }

    // DELETE
    public void delete(Long id) {
        if(id == null) {
            throw new BusinessException("Employee ID doesn't exist");
        }
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent()) {
            throw new BusinessException("Employee ID is not found");
        }
        employeeRepository.deleteById(id);
    }
}
