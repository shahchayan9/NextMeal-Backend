package com.nextmeal.reservation_handler_service.model.util;

import com.nextmeal.reservation_handler_service.model.ReservationResponse;

import java.sql.Timestamp;

public class ReservationResponseMapper {

    public static ReservationResponse mapToReservationResponse(Object[] result) {
        return new ReservationResponse(
                (String) result[0],
                (String) result[1],
                (String) result[2],
                (String) result[3],
                ((Timestamp) result[4]).toLocalDateTime(),
                ((Number) result[5]).intValue(),
                (String) result[6]
        );
    }
}
