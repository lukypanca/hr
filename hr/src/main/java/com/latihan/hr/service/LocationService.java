package com.latihan.hr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.latihan.hr.entity.Country;
import com.latihan.hr.entity.Location;
import com.latihan.hr.repository.CountryRepository;
import com.latihan.hr.repository.LocationRepository;
import com.latihan.hr.util.PaginationList;
import com.latihan.hr.wrapper.LocationWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CountryRepository countryRepository;

    // TO ENTITY
    public Location toEntity(LocationWrapper wrapper) {
        Location entity = new Location();
        if (wrapper.getLocationId() != null) {
            entity = locationRepository.getById(wrapper.getLocationId());
        }
        entity.setStreetAddress(wrapper.getStreetAddress());
        entity.setPostalCode(wrapper.getPostalCode());
        entity.setCity(wrapper.getCity());
        entity.setStateProvince(wrapper.getStateProvince());
        Optional<Country> optionalCountry = countryRepository.findById(wrapper.getCountryId());
        Country country = optionalCountry.isPresent() ? optionalCountry.get() : null;
        entity.setCountry(country);
        entity.setCreatedDate(wrapper.getCreatedDate());

        return entity;
    }

    // TO WRAPPER
    public LocationWrapper toWrapper(Location entity) {
        LocationWrapper wrapper = new LocationWrapper();
        wrapper.setLocationId(entity.getLocationId());
        wrapper.setStreetAddress(entity.getStreetAddress());
        wrapper.setPostalCode(entity.getPostalCode());
        wrapper.setCity(entity.getCity());
        wrapper.setStateProvince(entity.getStateProvince());
        wrapper.setCountryId(entity.getCountry() != null ? entity.getCountry().getCountryId() : null);
        wrapper.setCountryName(entity.getCountry() != null ? entity.getCountry().getCountryName() : null);
        wrapper.setCreatedDate(entity.getCreatedDate());

        return wrapper;
    }

    // TO WRAPPER LIST
    public List<LocationWrapper> toWrapperList(List<Location> entityList) {
        List<LocationWrapper> wrapperList = new ArrayList<LocationWrapper>();
        for (Location entity : entityList) {
            LocationWrapper wrapper = toWrapper(entity);
            wrapperList.add(wrapper);
        }
        return wrapperList;
    }

    // TO PAGINATION LIST
    public PaginationList<LocationWrapper, Location> toPaginationList(Page<Location> entityPage) {
        List<Location> locationList = entityPage.getContent();
        List<LocationWrapper> wrapperList = toWrapperList(locationList);
        PaginationList<LocationWrapper, Location> paginationList = new PaginationList<LocationWrapper, Location>(wrapperList, entityPage);
        return paginationList;
    }

    // FIND ALL
    public List<LocationWrapper> findAll() {
        List<Location> locationList = locationRepository.findAll();
        return toWrapperList(locationList); 
    }

    // FIND ALL WITH PAGINATION
    public PaginationList<LocationWrapper, Location> findAllWithPagination(int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("locationId").ascending());
        return toPaginationList(locationRepository.findAll(paging));
    }

    // GET BY ID
    public LocationWrapper getById(Long id) {
        Location location = locationRepository.getById(id);
        return toWrapper(location);
    }

    // SAVE
    public LocationWrapper save(LocationWrapper wrapper) {
        Location location = locationRepository.save(toEntity(wrapper));
        return toWrapper(location);
    }

    // DELETE
    public void delete(Long id) {
        locationRepository.deleteById(id);
    }

    // FIND BY ALL CATEGORIES
    public List<LocationWrapper> getByAllCategories(String all) {
        List<Location> locationList = locationRepository.getByAllCategories(all);
        return toWrapperList(locationList);
    }

    public List<LocationWrapper> getByCountryName(String countryName) {
		List<Location> countryNameList = locationRepository.findByCountryNameContainingIgnoreCas(countryName);
		return toWrapperList(countryNameList);
	}

}
