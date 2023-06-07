package com.solvd.db.model;

public class Address {
    private int addressId;
    private String line1;
    private String line2;
    private String zipCode;
    private City city;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city=" + city +
                '}';
    }
}
