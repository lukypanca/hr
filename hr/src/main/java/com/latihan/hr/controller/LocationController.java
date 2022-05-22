package com.latihan.hr.controller;

import com.latihan.hr.entity.Location;
import com.latihan.hr.service.LocationService;
import com.latihan.hr.util.DataResponse;
import com.latihan.hr.util.DataResponseList;
import com.latihan.hr.util.DataResponsePagination;
import com.latihan.hr.wrapper.LocationWrapper;

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
@RequestMapping(value = "/location")
public class LocationController {
    
    @Autowired
    LocationService locationService;

    // FIND ALL
    @GetMapping(path = "/findAll")
    DataResponseList<LocationWrapper> findAll() {
        return new DataResponseList<LocationWrapper>(locationService.findAll());
    }

    // FIND ALL WITH PAGINATION
    @GetMapping(path = "/findAllWithPagination")
    DataResponsePagination<LocationWrapper, Location> findAllWithPagination(@RequestParam int page, @RequestParam int size) {
        return new DataResponsePagination<LocationWrapper, Location>(locationService.findAllWithPagination(page, size));
    }

    // GET BY ID
    @GetMapping(path = "/getById")
    DataResponse<LocationWrapper> findById(@RequestParam Long locationId) {
        return new DataResponse<LocationWrapper>(locationService.getById(locationId));
    }

    // CREATE
    @PostMapping(path = "/post")
    DataResponse<LocationWrapper> create(@RequestBody LocationWrapper createWrapper) {
        return new DataResponse<LocationWrapper>(locationService.save(createWrapper));
    }

    // UPDATE
    @PutMapping(path = "/put")
    DataResponse<LocationWrapper> update(@RequestBody LocationWrapper updateWrapper) {
        return new DataResponse<LocationWrapper>(locationService.save(updateWrapper));
    }

    // DELETE
    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam Long locationId) {
        locationService.delete(locationId);
    }

    // GET BY ALL CATEGORIES
    @GetMapping(path = "/getByAllCategories")
    DataResponseList<LocationWrapper> getByAllCategories(@RequestParam String all) {
        return new DataResponseList<LocationWrapper>(locationService.getByAllCategories(all));
    }

    @GetMapping(path = "/getByCountryName")
	DataResponseList<LocationWrapper> getByCountryName(@RequestParam("countryName") String countryName){
		return new DataResponseList<LocationWrapper>(locationService.getByCountryName(countryName));
	}

}
