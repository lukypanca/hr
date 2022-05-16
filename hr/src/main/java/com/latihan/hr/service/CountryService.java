package com.latihan.hr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.latihan.hr.entity.Country;
import com.latihan.hr.entity.Region;
import com.latihan.hr.repository.CountryRepository;
import com.latihan.hr.repository.RegionRepository;
import com.latihan.hr.util.PaginationList;
import com.latihan.hr.wrapper.CountryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// import javafx.scene.control.Pagination;

@Service
@Transactional
public class CountryService {
    
    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    // TO ENTITY
    private Country toEntity(CountryWrapper wrapper) {
        Country entity = new Country();
        entity.setCountryId(wrapper.getCountryId());
        entity.setCountryName(wrapper.getCountryName());
        Optional<Region> optionalRegion = regionRepository.findById(wrapper.getRegionId());
        Region region = optionalRegion.isPresent() ? optionalRegion.get() : null;
        entity.setRegion(region);

        return entity;
    }

    // TO WRAPPER
    private CountryWrapper toWrapper(Country entity) {
        CountryWrapper wrapper = new CountryWrapper();
        wrapper.setCountryId(entity.getCountryId());
        wrapper.setCountryName(entity.getCountryName());
        wrapper.setRegionId(entity.getRegion() != null ? entity.getRegion().getRegionId() : null);

        return wrapper;
    }

    // TO WRAPPER LIST
    private List<CountryWrapper> toWrapperList(List<Country> entityList) {
        List<CountryWrapper> wrapperList = new ArrayList<CountryWrapper>();
        for(Country entity : entityList) {
            CountryWrapper wrapper = toWrapper(entity);
            wrapperList.add(wrapper);
        }
        return wrapperList;
    }

    // TO PAGINATION LIST
    private PaginationList<CountryWrapper, Country> toPaginationList(Page<Country> entityPage) {
        List<Country> countryList = entityPage.getContent();
        List<CountryWrapper> countryWrapperList = toWrapperList(countryList);
        PaginationList<CountryWrapper, Country> paginationList = new PaginationList<CountryWrapper, Country>(countryWrapperList, entityPage);
        return paginationList;
    }

    // FIND ALL WITH PAGINATION
    public PaginationList<CountryWrapper, Country> findAllWithPagination(int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("countryName").ascending());
        Page<Country> countryPage = countryRepository.findAll(paging);
        return toPaginationList(countryPage);
    }

    // FIND ALL
    public List<CountryWrapper> findAll() {
        List<Country> countryList = countryRepository.findAll();
        return toWrapperList(countryList);
    }

    // GET BY ID
    public CountryWrapper getById(String id){
        Country country = countryRepository.getById(id);
        return toWrapper(country);
    }

    // GET BY COUNTRY NAME
    public PaginationList<CountryWrapper, Country> findByCountryName(String countryName, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("countryName").ascending());
        Page<Country> countryPage = countryRepository.findByCountryNameContainingIgnoreCase(countryName, paging);
        return toPaginationList(countryPage);
    }

    // SAVE
    public CountryWrapper save(CountryWrapper wrapper) {
        Country country = countryRepository.save(toEntity(wrapper));
        return toWrapper(country); 
    }

    // DELETE
    public void delete(String id) {
        countryRepository.deleteById(id);
    }

}
