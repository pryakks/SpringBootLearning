package com.payment.service;

import com.payment.dto.BookingRequest;
import com.payment.dto.BookingResponse;

public interface BookingService {
    public BookingResponse bookTicket(BookingRequest bookingRequest);
}
