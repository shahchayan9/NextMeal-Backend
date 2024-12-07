package com.nextmeal.reservation_handler_service.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReservationResponse {
    private String reservationId;
    private String userId;
    private String restaurantId;
    private String restaurantName;
    private LocalDateTime slot;
    private Integer numberOfPeople;
    private String status;

    public ReservationResponse(String reservationId, String userId, String restaurantId, String restaurantName, LocalDateTime slot, Integer numberOfPeople, String status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.slot = slot;
        this.numberOfPeople = numberOfPeople;
        this.status = status;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDateTime getSlot() {
        return slot;
    }

    public void setSlot(LocalDateTime slot) {
        this.slot = slot;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationResponse that = (ReservationResponse) o;
        return Objects.equals(reservationId, that.reservationId)
                && Objects.equals(userId, that.userId)
                && Objects.equals(restaurantId, that.restaurantId)
                && Objects.equals(restaurantName, that.restaurantName)
                && Objects.equals(slot, that.slot)
                && Objects.equals(numberOfPeople, that.numberOfPeople)
                && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, userId, restaurantId, restaurantName, slot, numberOfPeople, status);
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", slot=" + slot +
                ", numberOfPeople=" + numberOfPeople +
                ", status='" + status + '\'' +
                '}';
    }
}
