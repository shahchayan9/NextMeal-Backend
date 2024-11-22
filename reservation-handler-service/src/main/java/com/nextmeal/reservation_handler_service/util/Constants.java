package com.nextmeal.reservation_handler_service.util;

public class Constants {
    public static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/529088288756/ReservationQueue.fifo";

    public static final String REQUEST_TYPE_RESERVE = "RESERVE";
    public static final String REQUEST_TYPE_CANCEL = "CANCEL";

    public static final Integer MAX_CAPACITY = 20;
}