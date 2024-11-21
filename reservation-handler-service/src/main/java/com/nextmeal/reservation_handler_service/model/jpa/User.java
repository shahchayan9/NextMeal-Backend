package com.nextmeal.reservation_handler_service.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "yelping_since")
    private LocalDateTime yelpingSince;

    @Column(name = "useful")
    private Integer useful;

    @Column(name = "funny")
    private Integer funny;

    @Column(name = "cool")
    private Integer cool;

    public User() {}

    public User(String userId, String name, Integer reviewCount, LocalDateTime yelpingSince,
                Integer useful, Integer funny, Integer cool) {
        this.userId = userId;
        this.name = name;
        this.reviewCount = reviewCount;
        this.yelpingSince = yelpingSince;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public LocalDateTime getYelpingSince() {
        return yelpingSince;
    }

    public void setYelpingSince(LocalDateTime yelpingSince) {
        this.yelpingSince = yelpingSince;
    }

    public Integer getUseful() {
        return useful;
    }

    public void setUseful(Integer useful) {
        this.useful = useful;
    }

    public Integer getFunny() {
        return funny;
    }

    public void setFunny(Integer funny) {
        this.funny = funny;
    }

    public Integer getCool() {
        return cool;
    }

    public void setCool(Integer cool) {
        this.cool = cool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", reviewCount=" + reviewCount +
                ", yelpingSince=" + yelpingSince +
                ", useful=" + useful +
                ", funny=" + funny +
                ", cool=" + cool +
                '}';
    }
}
