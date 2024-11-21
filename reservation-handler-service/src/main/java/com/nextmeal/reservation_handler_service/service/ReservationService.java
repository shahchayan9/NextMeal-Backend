package com.nextmeal.reservation_handler_service.service;

import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import com.nextmeal.reservation_handler_service.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public void saveReservation(Reservation reservation) {
        repository.save(reservation);
    }

    public Optional<String> findReservationByDetails(String userId, String restaurantId, LocalDateTime slot, Integer numberOfPeople) {
        return repository.findReservationByDetails(userId, restaurantId, slot, numberOfPeople);
    }

    public void deleteReservationById(String reservationId) {
        repository.deleteById(reservationId);
    }
}
