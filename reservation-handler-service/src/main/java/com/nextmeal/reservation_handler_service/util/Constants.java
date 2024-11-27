package com.nextmeal.reservation_handler_service.util;

public class Constants {
    public static final String QUEUE_URL = "https://sqs.us-west-1.amazonaws.com/423623844604/ReservationSystemQueue.fifo";

    public static final String REQUEST_TYPE_RESERVE = "RESERVE";
    public static final String REQUEST_TYPE_CANCEL = "CANCEL";

    public static final Integer MAX_CAPACITY = 20;
}