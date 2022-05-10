package com.product.service.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private Long Id;
    private String code;
    private BigDecimal discount;
    private String expDate;
}
