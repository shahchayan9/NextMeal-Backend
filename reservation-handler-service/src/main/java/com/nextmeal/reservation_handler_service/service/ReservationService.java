package com.nextmeal.reservation_handler_service.service;

import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import com.nextmeal.reservation_handler_service.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.nextmeal.reservation_handler_service.util.Constants.MAX_CAPACITY;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    private final static Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public void saveReservation(Reservation reservation) {
        repository.save(reservation);
    }

    public void deleteReservationById(String reservationId) {
        repository.deleteById(reservationId);
    }

    public List<Reservation> findReservationsForUser(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<String> findReservationByDetails(String userId, String restaurantId, LocalDateTime slot, Integer numberOfPeople) {
        logger.info(slot.toString());
        return repository.findReservationByDetails(userId, restaurantId, slot, numberOfPeople);
    }

    public boolean checkReservationAvailability(String restaurantId, LocalDateTime slot, Integer numberOfPeople) {
        Integer booked;
        Optional<Integer> bookedOptional = repository.findTotalNumberOfPeopleByRestaurantAndSlot(restaurantId, slot);

        if (bookedOptional.isPresent()) {
            booked = bookedOptional.get();
            logger.info(booked.toString());
            return (booked + numberOfPeople <= MAX_CAPACITY);
        }

        return true;
    }
}