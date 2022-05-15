package com.latihan.hr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "regions")
@Getter
@Setter
public class Region {

//    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long regionId;
    @Column(name = "region_name")
    private String regionName;
}
