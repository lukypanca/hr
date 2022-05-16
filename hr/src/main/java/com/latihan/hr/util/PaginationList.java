package com.latihan.hr.util;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationList<T, U> {
    
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalRowCount;
    private List<T> data;

    public PaginationList(List<T> datalist, Page<U> list) {
        page = list.getNumber();
        size = list.getSize();
        totalPages = list.getTotalPages();
        totalRowCount = list.getTotalElements();
        this.data = datalist;
    }
}
