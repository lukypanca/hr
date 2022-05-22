package com.latihan.hr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobHistoryId implements Serializable {
    
    // private static final Long serialVersionUID = -4907182680249802382L;

    @Id
    private Long employeeId;
    @Id
    private Date startDate;

    public JobHistoryId() {
		super();
	}

	public JobHistoryId(Long employeeId, Date startDate) {
		super();
		this.employeeId = employeeId;
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobHistoryId other = (JobHistoryId) obj;
		return Objects.equals(employeeId, other.employeeId) && Objects.equals(startDate, other.startDate);
	}

}
