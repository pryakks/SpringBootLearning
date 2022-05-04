package com.payment.dto;

import com.payment.model.PassengerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BookingResponse {
    private String status;
    private Double totalFare;
    private String pnr;
    private PassengerInfo passengerInfo;
}
