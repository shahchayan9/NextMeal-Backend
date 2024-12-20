package com.nextmeal.reservation_handler_service.repository;

import com.nextmeal.reservation_handler_service.model.ReservationResponse;
import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    @Query("SELECT r.reservationId FROM Reservation r " +
            "WHERE r.userId = :userId " +
            "AND r.restaurantId = :restaurantId " +
            "AND r.slot = :slot " +
            "AND r.numberOfPeople = :numberOfPeople " +
            "AND r.status = 'CONFIRMED'")
    Optional<String> findReservationByDetails(
            @Param("userId") String userId,
            @Param("restaurantId") String restaurantId,
            @Param("slot") LocalDateTime slot,
            @Param("numberOfPeople") Integer numberOfPeople
    );

    @Query("SELECT SUM(r.numberOfPeople) FROM Reservation r " +
            "WHERE r.restaurantId = :restaurantId " +
            "AND r.slot = :slot " +
            "AND r.status = 'CONFIRMED'")
    Optional<Integer> findTotalNumberOfPeopleByRestaurantAndSlot(
            @Param("restaurantId") String restaurantId,
            @Param("slot") LocalDateTime slot
    );

    @Query(value = "SELECT rv.reservation_id, rv.user_id, rv.business_id, rs.name, rv.slot, rv.number_of_people, rv.status " +
            "FROM reservations rv " +
            "LEFT JOIN restaurants rs ON rv.business_id = rs.business_id " +
            "WHERE rv.user_id = :userId", nativeQuery = true)
    List<Object[]> findByUserIdWithRestaurantName(@Param("userId") String userId);

}