package com.latihan.hr.service;

import com.latihan.hr.wrapper.RegionWrapper;
import com.latihan.hr.entity.Region;
import com.latihan.hr.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RegionService {

    @Autowired
    RegionRepository regionRepository;

    private Region toEntity(RegionWrapper wrapper) {
        Region entity = new Region();
        if (wrapper.getRegionId() != null) {
            entity = regionRepository.getById(wrapper.getRegionId());
        }
        entity.setRegionName(wrapper.getRegionName());

        return entity;
    }

    private RegionWrapper toWrapper(Region entity) {
        RegionWrapper wrapper = new RegionWrapper();
        wrapper.setRegionId(entity.getRegionId());
        wrapper.setRegionName(entity.getRegionName());

        return wrapper;
    }

    private List<RegionWrapper> toWrapperList(List<Region> entityList) {
        List<RegionWrapper> wrapperList = new ArrayList<RegionWrapper>();
        for (Region entity : entityList) {
            RegionWrapper wrapper = toWrapper(entity);
            wrapperList.add(wrapper);
        }
        return wrapperList;
    }

    // FIND ALL
    public List<RegionWrapper> findAll() {
        List<Region> regionList = regionRepository.findAll();
        return toWrapperList(regionList);
    };

    // FIND ALL WITH PAGINATION
    public List<RegionWrapper> findAllWitPagination(int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("regionId").ascending());
        Page<Region> regionPage = regionRepository.findAll(paging);
        List<Region> regionList = regionPage.getContent();
        List<RegionWrapper> regionWrapperList = toWrapperList(regionList);
        return regionWrapperList;
    }

    // CREATE AND UPDATE
    public RegionWrapper save(RegionWrapper wrapper) {
        Region region = regionRepository.save(toEntity(wrapper));
        return toWrapper(region);
    }

    // DELETE
    public void delete(Long id) {
        regionRepository.deleteById(id);
    }

    // GET BY ID
    public RegionWrapper getById(Long id) {
        Region region = regionRepository.getById(id);
        return toWrapper(region);
    }

    // FIND BY REGION NAME
    public List<RegionWrapper> findByRegionName(String regionName, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("regionName").ascending());
        Page<Region> pageRegion = regionRepository.findByRegionNameContainingIgnoreCase(regionName, paging);
        List<Region> listRegion = pageRegion.getContent();
        List<RegionWrapper> listRegionWrapper = toWrapperList(listRegion);
        return listRegionWrapper;
    }

}
