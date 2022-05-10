package com.product.service.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductResponse {
    private Long Id;
    private String name;
    private String description;
    private BigDecimal price;
}
