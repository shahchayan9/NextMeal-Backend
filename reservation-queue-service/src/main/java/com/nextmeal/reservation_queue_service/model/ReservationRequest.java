package com.nextmeal.reservation_queue_service.model;

import java.util.Date;
import java.util.Objects;

public class ReservationRequest {
    private String userId;
    private String restaurantId;
    private Integer numOfPeople;
    private String slot;

    public ReservationRequest(String userId, String restaurantId, Integer numOfPeople, String slot) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.numOfPeople = numOfPeople;
        this.slot = slot;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(Integer numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRequest that = (ReservationRequest) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(restaurantId, that.restaurantId)
                && Objects.equals(numOfPeople, that.numOfPeople)
                && Objects.equals(slot, that.slot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, numOfPeople, slot);
    }

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "userId='" + userId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", numOfPeople=" + numOfPeople +
                ", slot=" + slot +
                '}';
    }
}