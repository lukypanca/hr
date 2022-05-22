package com.latihan.hr.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.latihan.hr.entity.Department;
import com.latihan.hr.entity.Location;
import com.latihan.hr.repository.DepartmentRepository;
import com.latihan.hr.repository.LocationRepository;
import com.latihan.hr.util.PaginationList;
import com.latihan.hr.wrapper.DepartmentWrapper;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DepartmentService {
    
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    LocationRepository locationRepository;

    // TO ENTITY
    public Department toEntity(DepartmentWrapper wrapper) {
        Department entity = new Department();
        if (wrapper.getDepartmentId() != null) {
            entity = departmentRepository.getById(wrapper.getDepartmentId());
        }
        entity.setDepartmentName(wrapper.getDepartmentName());
        entity.setManagerId(wrapper.getManagerId());
        Location location = locationRepository.getById(wrapper.getLocationId());
        entity.setLocation(location);

        return entity;
    }

    // TO WRAPPER
    public DepartmentWrapper toWrapper(Department entity) {
        DepartmentWrapper wrapper = new DepartmentWrapper();
        wrapper.setDepartmentId(entity.getDepartmentId());
        wrapper.setDepartmentName(entity.getDepartmentName());
        wrapper.setManagerId(entity.getManagerId());
        wrapper.setLocationId(entity.getLocation() != null ? entity.getLocation().getLocationId() : null);
        wrapper.setCity(entity.getLocation() != null ? entity.getLocation().getCity() : null);

        return wrapper;
    }

    // TO WRAPPER LIST
    public List<DepartmentWrapper> toWrapperList(List<Department> entityList) {
        List<DepartmentWrapper> departmentList = new ArrayList<DepartmentWrapper>();
        for (Department entity : entityList) {
            DepartmentWrapper wrapper = toWrapper(entity);
            departmentList.add(wrapper);
        }
        return departmentList;
    }

    // TO PAGINATION LIST
    private PaginationList<DepartmentWrapper, Department> toPaginationList(Page<Department> entityPage) {
        List<Department> entityList = entityPage.getContent();
        List<DepartmentWrapper> wrapperList = toWrapperList(entityList);
        PaginationList<DepartmentWrapper, Department> paginationList = new PaginationList<>(wrapperList, entityPage);
        return paginationList;
    }

    // FIND ALL
    public List<DepartmentWrapper> findAll() {
        List<Department> departmentList = departmentRepository.findAll();
        return toWrapperList(departmentList);
    }

    // FIND ALL WITH PAGINATION
    public PaginationList<DepartmentWrapper, Department> findAllWithPagination(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return toPaginationList(departmentRepository.findAll(paging));
    }

    // GET BY ID
    public DepartmentWrapper getById(Long id) {
        Department department = departmentRepository.getById(id);
        return toWrapper(department);
    }

    // SAVE
    public DepartmentWrapper save(DepartmentWrapper wrapper) {
        Department department = departmentRepository.save(toEntity(wrapper));
        return toWrapper(department);
    }

    // DELETE
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    

}
