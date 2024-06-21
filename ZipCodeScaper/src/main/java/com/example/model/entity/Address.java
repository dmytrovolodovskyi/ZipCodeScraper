package com.example.model.entity;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "zip_tb", indexes = {
        @Index(name = "state_zip", columnList = "Country, ZIP, City")
})
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Country")
    private String country;

    @Column(name = "Province")
    private String province;

    @Column(name = "County")
    private String county;

    @Column(name = "District")
    private String district;

    @Column(name = "City")
    private String city;

    @Column(name = "Latitude", nullable = false, columnDefinition = "DOUBLE(16,13) DEFAULT '0.0000000000000'")
    private double latitude;

    @Column(name = "Longitude", nullable = false, columnDefinition = "DOUBLE(16,13) DEFAULT '0.0000000000000'")
    private double longitude;

    @Column(name = "ZIP")
    private String zip;
}
