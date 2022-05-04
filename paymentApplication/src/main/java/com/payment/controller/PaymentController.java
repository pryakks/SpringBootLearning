package com.payment.controller;

import com.payment.dto.BookingRequest;
import com.payment.dto.BookingResponse;
import com.payment.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponse bookingTicket(@RequestBody BookingRequest bookingRequest){
        return bookingService.bookTicket(bookingRequest);
    }
}
