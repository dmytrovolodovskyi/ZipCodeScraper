package com.example.model.entity;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country_tb")
@Setter
public class Country {

    @Id
    @Column(name = "Country")
    private String country;

    @Column(name = "Country_iso")
    private String countryIso;

    @Column(name = "Name")
    private String name;
}
