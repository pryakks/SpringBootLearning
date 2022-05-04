package com.payment.service;

import com.payment.dto.BookingRequest;
import com.payment.dto.BookingResponse;
import com.payment.exception.InsufficientBalanceException;
import com.payment.model.PassengerInfo;
import com.payment.model.PaymentInfo;
import com.payment.repository.PassengerInfoRepository;
import com.payment.repository.PaymentInfoRepository;
import com.payment.utility.PaymentGatewaySimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    @Autowired
    public BookingResponse bookingResponse;
    @Transactional(readOnly = false, rollbackFor = {InsufficientBalanceException.class, Exception.class},
    isolation = Isolation.READ_COMMITTED)
    public BookingResponse bookTicket(BookingRequest bookingRequest){

        PassengerInfo passengerInfo = passengerInfoRepository.save(bookingRequest.getPassengerInfo());

        PaymentInfo paymentInfo = bookingRequest.getPaymentInfo();
        PaymentGatewaySimulator.validateFareBalance(paymentInfo.getAccountNo(), passengerInfo.getFare());
        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfoRepository.save(paymentInfo);

        bookingResponse.setStatus("Success");
        bookingResponse.setPnr(UUID.randomUUID().toString().split("-")[0]);
        bookingResponse.setPassengerInfo(passengerInfo);
        bookingResponse.setTotalFare(passengerInfo.getFare());

        return bookingResponse;
    }
}
