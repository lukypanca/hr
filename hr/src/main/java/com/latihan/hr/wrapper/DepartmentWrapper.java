package com.latihan.hr.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentWrapper {

    private Long departmentId;
    private String departmentName;
    private Long managerId;
    private Long locationId;
    private String city;
    
}
