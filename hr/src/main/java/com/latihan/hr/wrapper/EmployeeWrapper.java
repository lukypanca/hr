package com.latihan.hr.wrapper;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeWrapper {
    
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private String jobId;
    private String jobTitle;
    private Long salary;
    private Long commissionPct;
    private Long managerId;
    private String managerName;
    private Long departmentId;
    private String departmentName;

}
