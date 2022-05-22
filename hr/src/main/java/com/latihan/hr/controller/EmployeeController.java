package com.latihan.hr.controller;

import com.latihan.hr.entity.Employee;
import com.latihan.hr.service.EmployeeService;
import com.latihan.hr.util.DataResponse;
import com.latihan.hr.util.DataResponseList;
import com.latihan.hr.util.DataResponsePagination;
import com.latihan.hr.wrapper.EmployeeWrapper;

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
@RequestMapping(value = "/employee")
public class EmployeeController {
    
    @Autowired
    EmployeeService employeeService;

    // FIND ALL
    @GetMapping(path = "/findAll")
    DataResponseList<EmployeeWrapper> findAll() {
        return new DataResponseList<EmployeeWrapper>(employeeService.findAll());
    }

    // FIND ALL WITH PAGINATION
    @GetMapping(path = "/findAllWithPagination")
    DataResponsePagination<EmployeeWrapper, Employee> findAllWitPagination(@RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<EmployeeWrapper, Employee>(employeeService.findAllWithPaginationList(page, size));
    }
    
    // GET BY ID
    @GetMapping(path = "/getById")
    DataResponse<EmployeeWrapper> getById(@RequestParam Long employeeId) {
        return new DataResponse<EmployeeWrapper>(employeeService.getById(employeeId));
    }

    // POST
    @PostMapping(path = "/post")
    DataResponse<EmployeeWrapper> create(@RequestBody EmployeeWrapper createWrapper) {
        return new DataResponse<EmployeeWrapper>(employeeService.save(createWrapper));
    }

    // PUT
    @PutMapping(path = "/put")
    DataResponse<EmployeeWrapper> update(@RequestBody EmployeeWrapper updateWrapper) {
        return new DataResponse<EmployeeWrapper>(employeeService.save(updateWrapper));
    }

    // DELETE
    @DeleteMapping(path = "/delete")
    public void delete(Long employeeId) {
        employeeService.delete(employeeId);
    }

}
