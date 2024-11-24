package com.nextmeal.reservation_handler_service.service;

import com.nextmeal.reservation_handler_service.model.ReservationRequest;
import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import com.nextmeal.reservation_handler_service.model.jpa.Restaurant;
import com.nextmeal.reservation_handler_service.model.jpa.User;
import com.nextmeal.reservation_handler_service.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.nextmeal.reservation_handler_service.util.Constants.MAX_CAPACITY;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final UserService userService;
    private final RestaurantService restaurantService;

    private final static Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(ReservationRepository repository, UserService userService, RestaurantService restaurantService) {
        this.repository = repository;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public String createReservation(ReservationRequest request) {
        Optional<User> userOptional = userService.getUserById(request.getUserId());
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(request.getRestaurantId());

        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            User user = userOptional.get();
            Restaurant restaurant = restaurantOptional.get();
            LocalDateTime slot = LocalDateTime.parse(request.getSlot()).truncatedTo(ChronoUnit.SECONDS);

            if (checkReservationAvailability(restaurant.getBusinessId(), slot, request.getNumOfPeople())) {
                Reservation reservation = new Reservation(user.getUserId(), restaurant.getBusinessId(), slot, request.getNumOfPeople(), "CONFIRMED");
                saveReservation(reservation);

                response = "Hello " + user.getName() + ", your reservation for " +
                        request.getNumOfPeople() + " at " + restaurant.getName() + " on " + slot.toString() +
                        " has been successfully confirmed. Thank you!";
            } else {
                Reservation reservation = new Reservation(user.getUserId(), restaurant.getBusinessId(), slot, request.getNumOfPeople(), "REJECTED");
                saveReservation(reservation);

                response = "Hello " + user.getName() + ", your reservation for " +
                        request.getNumOfPeople() + " at " + restaurant.getName() + " on " + slot.toString() +
                        " has been unfortunately rejected due to constraints. Please try a different slot!";
            }
        }

        return response;
    }

    public String cancelReservation(ReservationRequest request) {
        Optional<User> userOptional = userService.getUserById(request.getUserId());
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(request.getRestaurantId());

        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            User user = userOptional.get();
            Restaurant restaurant = restaurantOptional.get();
            LocalDateTime slot = LocalDateTime.parse(request.getSlot()).truncatedTo(ChronoUnit.SECONDS);

            Optional<String> reservationIdOptional = findReservationByDetails(user.getUserId(), restaurant.getBusinessId(), slot, request.getNumOfPeople());

            response = "Reservation not found";

            if (reservationIdOptional.isPresent()) {
                String reservationId = reservationIdOptional.get();

                deleteReservationById(reservationId);

                response = "Hello " + user.getName() + ", your reservation for " +
                        request.getNumOfPeople() + " at " + restaurant.getName() + " on " + slot.toString() +
                        " has been successfully cancelled. Thank you!";
            }
        }

        return response;
    }

    public List<Reservation> getReservationsForUser(String userId) {
        List<Reservation> reservationsByUser = null;
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isPresent()) {
            reservationsByUser = findReservationsForUser(userId);
            logger.info("Found {} reservations", reservationsByUser.size());
        }

        return reservationsByUser;
    }

    private void saveReservation(Reservation reservation) {
        repository.save(reservation);
    }

    private void deleteReservationById(String reservationId) {
        repository.deleteById(reservationId);
    }

    private List<Reservation> findReservationsForUser(String userId) {
        return repository.findByUserId(userId);
    }

    private Optional<String> findReservationByDetails(String userId, String restaurantId, LocalDateTime slot, Integer numberOfPeople) {
        logger.info(slot.toString());
        return repository.findReservationByDetails(userId, restaurantId, slot, numberOfPeople);
    }

    private boolean checkReservationAvailability(String restaurantId, LocalDateTime slot, Integer numberOfPeople) {
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