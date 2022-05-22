package com.latihan.hr.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobWrapper {
    
    private String jobId;
    private String jobTitle;
    private Long minSalary;
    private Long maxSalary;

}
