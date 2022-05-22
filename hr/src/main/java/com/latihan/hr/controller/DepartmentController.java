package com.latihan.hr.controller;

import com.latihan.hr.entity.Department;
import com.latihan.hr.service.DepartmentService;
import com.latihan.hr.util.DataResponse;
import com.latihan.hr.util.DataResponseList;
import com.latihan.hr.util.DataResponsePagination;
import com.latihan.hr.wrapper.DepartmentWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping(value = "/department")
public class DepartmentController {
    
    @Autowired
    DepartmentService departmentService;

    // FIND ALL
    @GetMapping(path = "/findAll")
    DataResponseList<DepartmentWrapper> findAll() {
        return new DataResponseList<DepartmentWrapper>(departmentService.findAll());
    }

    // FIND ALL WITH PAGINATION
    @GetMapping(path = "/findAllWithPagination")
    DataResponsePagination<DepartmentWrapper, Department> findAllWPagination(@RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<DepartmentWrapper, Department>(departmentService.findAllWithPagination(page, size));
    }

    // GET BY ID
    @GetMapping(path = "/getById")
    DataResponse<DepartmentWrapper> getById(@RequestParam Long departmentId) {
        try {
            DepartmentWrapper department = departmentService.getById(departmentId);
            return new DataResponse<DepartmentWrapper>(department);
        } catch (Exception e) {
            return new DataResponse<DepartmentWrapper>(false, "Department dengan id " + departmentId + " tidak ditemukan");
        }
    }

    // CREATE
    @PostMapping(path = "/post")
    DataResponse<DepartmentWrapper> create(@RequestBody DepartmentWrapper createWrapper) {
        return new DataResponse<DepartmentWrapper>(departmentService.save(createWrapper));
    }

    // UPDATE
    @PutMapping(path = "/put")
    DataResponse<DepartmentWrapper> update(@RequestBody DepartmentWrapper updateWrapper) {
        try {
            return new DataResponse<DepartmentWrapper>(departmentService.save(updateWrapper));
        } catch (Exception e) {
            String errorMessage;
            if (e.getMessage().contains("Department")) {
                errorMessage = "Department dengan ID " + updateWrapper.getDepartmentId() + " tidak ditemukan";
            } else if (e.getMessage().contains("Location")) {
                errorMessage = "Location dengan ID " + updateWrapper.getLocationId() + "tidak ditemukan";
            } else {
                errorMessage = e.getMessage();
            }
            return new DataResponse<DepartmentWrapper>(false, errorMessage);
        }
    }

    // DELETE
    @DeleteMapping(path = "/delete")
    public DataResponse<DepartmentWrapper> delete(@RequestParam Long departmentId) {
        try {
            departmentService.delete(departmentId);
            return new DataResponse<DepartmentWrapper>(true, "Delete berhasil");
        } catch (Exception e) {
            return new DataResponse<DepartmentWrapper>(false, "Department dengan id " + departmentId + " tidak ditemukan");
        }
    }

}
