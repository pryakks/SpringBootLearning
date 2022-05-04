package com.payment.dto;

import com.payment.model.PassengerInfo;
import com.payment.model.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BookingRequest {
    private PassengerInfo passengerInfo;
    private PaymentInfo paymentInfo;
}
