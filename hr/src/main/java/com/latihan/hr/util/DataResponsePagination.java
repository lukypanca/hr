package com.latihan.hr.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponsePagination<E, U> extends DataResponseList<E>{

    public DataResponsePagination(PaginationList<E, U> paginationList) {
		super(paginationList.getData());
		this.page = paginationList.getPage();
		this.size = paginationList.getSize();
		this.totalPages = paginationList.getTotalPages();
		this.totalRowCount = paginationList.getTotalRowCount();
		this.lastPage = (this.totalPages - 1) == this.page ? true : false;
	}

    public DataResponsePagination(boolean status, String message) {
        super(status, message);
    }

    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalRowCount;
    private boolean lastPage;


}
