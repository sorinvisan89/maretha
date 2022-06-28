package com.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "COUNTRIES")
public class CountryEntity {

    @Id
    @Column(name = "COUNTRY_NAME")
    private String countryName;

}
