package com.latihan.hr.controller;

import com.latihan.hr.service.RegionService;
import com.latihan.hr.wrapper.RegionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/regions")
public class RegionController {

    @Autowired
    RegionService regionService;

    // FIND ALL
    @GetMapping(path = "/findAll")
    public List<RegionWrapper> findAll() {
        return regionService.findAll();
    }

    // FIND ALL WITH PAGINATION
    @GetMapping(path = "/findAllWithPagination")
    public List<RegionWrapper> findAllWithPagination(@RequestParam int page, @RequestParam int size) {
        return regionService.findAllWitPagination(page, size);
    }

    // CREATE
    @PostMapping(path = "/post")
    public RegionWrapper save(@RequestBody RegionWrapper createWrapper) {
        return regionService.save(createWrapper);
    }

    // UPDATE
    @PutMapping(path = "/put")
    public RegionWrapper update(@RequestBody RegionWrapper updateWrapper) {
        return regionService.save(updateWrapper);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long regionId) {
        regionService.delete(regionId);
    }

    // GET BY ID
    @GetMapping(path = "getById")
    public RegionWrapper getById(@RequestParam("id") Long regionId) {
        return regionService.getById(regionId);
    }

    @GetMapping(path = "findByRegionName")
    public List<RegionWrapper> findByRegionName(@RequestParam String regionName, @RequestParam int page, @RequestParam int size){
        return regionService.findByRegionName(regionName, page, size);
    }

}
