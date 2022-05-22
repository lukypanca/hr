package com.latihan.hr.controller;

import com.latihan.hr.entity.Country;
import com.latihan.hr.service.CountryService;
// import com.latihan.hr.service.RegionService;
import com.latihan.hr.util.DataResponse;
import com.latihan.hr.util.DataResponseList;
import com.latihan.hr.util.DataResponsePagination;
import com.latihan.hr.wrapper.CountryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/country")
public class CountryController {
    
    @Autowired
    CountryService countryService;

    // FIND ALL
    @GetMapping(path = "/findAll")
    DataResponseList<CountryWrapper> findAll() {
        return new DataResponseList<CountryWrapper>(countryService.findAll());
    }

    // FIND ALL WITH PAGINATION
    @GetMapping(path = "/findAllWithPagination")
    DataResponsePagination<CountryWrapper, Country> findAllWithPagination(@RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<CountryWrapper, Country>(countryService.findAllWithPagination(page, size));
    }

    // GET BY ID
    @GetMapping(path = "/getById")
    DataResponse<CountryWrapper> getById(@RequestParam("id") String countryId) {
        return new DataResponse<CountryWrapper>(countryService.getById(countryId));
    }

    // GET BY COUNTRY NAME
    @GetMapping(path = "/getByCountryName")
    DataResponsePagination<CountryWrapper, Country> getByCountryName(@RequestParam String countryName, @RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<CountryWrapper, Country>(countryService.findByCountryName(countryName, page, size));
    }

    // CREATE
    @PostMapping(path = "/post")
    DataResponse<CountryWrapper> create(@RequestBody CountryWrapper createWrapper) {
        return new DataResponse<CountryWrapper>(countryService.save(createWrapper));
    }

    // UPDATE
    @PutMapping(path = "/put")
    DataResponse<CountryWrapper> update(@RequestBody CountryWrapper updateWrapper) {
        return new DataResponse<CountryWrapper>(countryService.save(updateWrapper));
    }

    //DELETE
    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam String countryId) {
        countryService.delete(countryId);
    }

}
