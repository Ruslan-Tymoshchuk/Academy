package ua.com.rtim.academy.domain;

import java.util.Objects;

public class Address {

    private Integer id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;
    private String postalCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String postalCode() {
        return postalCode;
    }

    public void setPostalCode(String index) {
        this.postalCode = index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, houseNumber, id, postalCode, region, street);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        return Objects.equals(city, other.city) && Objects.equals(country, other.country)
                && Objects.equals(houseNumber, other.houseNumber) && Objects.equals(id, other.id)
                && Objects.equals(postalCode, other.postalCode) && Objects.equals(region, other.region)
                && Objects.equals(street, other.street);
    }
}