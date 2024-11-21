package com.nextmeal.reservation_handler_service.repository;

import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    @Query("SELECT r.reservationId FROM Reservation r " +
            "WHERE r.userId = :userId " +
            "AND r.restaurantId = :restaurantId " +
            "AND r.slot = :slot " +
            "AND r.numberOfPeople = :numberOfPeople")
    Optional<String> findReservationByDetails(
            @Param("userId") String userId,
            @Param("restaurantId") String restaurantId,
            @Param("slot") LocalDateTime slot,
            @Param("numberOfPeople") Integer numberOfPeople
    );

}