package com.nextmeal.reservation_handler_service.model.jpa;

import com.nextmeal.reservation_handler_service.util.MapToJsonConverter;
import jakarta.persistence.*;

import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @Column(name = "business_id", nullable = false, unique = true)
    private String businessId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "stars")
    private Double stars;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "attributes", columnDefinition = "jsonb")
    @Convert(converter = MapToJsonConverter.class) // Custom converter to handle JSONB
    private Map<String, String> attributes;

    @Column(name = "categories", columnDefinition = "text")
    private String categories;

    @Column(name = "hours", columnDefinition = "jsonb")
    @Convert(converter = MapToJsonConverter.class) // Custom converter to handle JSONB
    private Map<String, String> hours;

    public Restaurant() {}

    public Restaurant(String businessId, String name, String address, String city, String state, String postalCode, Double latitude, Double longitude, Double stars, Integer reviewCount, Boolean isOpen, Map<String, String> attributes, String categories, Map<String, String> hours) {
        this.businessId = businessId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stars = stars;
        this.reviewCount = reviewCount;
        this.attributes = attributes;
        this.categories = categories;
        this.hours = hours;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Map<String, String> getHours() {
        return hours;
    }

    public void setHours(Map<String, String> hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(businessId, that.businessId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(businessId);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "businessId='" + businessId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stars=" + stars +
                ", reviewCount=" + reviewCount +
                ", attributes=" + attributes +
                ", categories='" + categories + '\'' +
                ", hours=" + hours +
                '}';
    }
}