package com.nextmeal.reservation_handler_service.model.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="reservations")
public class Reservation {

    @Id
    @Column(name = "reservation_id", nullable = false, unique = true)
    private String reservationId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "business_id", nullable = false)
    private String restaurantId;

    @Column(name = "slot", nullable = false)
    private LocalDateTime slot;

    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;

    @Column(name = "status", nullable = false)
    private String status;

    public Reservation() {}

    public Reservation(String userId, String restaurantId, LocalDateTime slot, Integer numberOfPeople, String status) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.slot = slot;
        this.numberOfPeople = numberOfPeople;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (this.reservationId == null)
            this.reservationId = UUID.randomUUID().toString();
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
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", userId='" + userId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", slot=" + slot +
                ", numberOfPeople=" + numberOfPeople +
                ", status='" + status + '\'' +
                '}';
    }
}