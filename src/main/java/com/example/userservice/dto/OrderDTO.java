package com.example.userservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDTO implements Serializable {
    protected Long id;
    private Double price;
    private Long productId;
    private String currency;
    private Double discount;
    private Long userId;
    private UserDTO user = new UserDTO();
}
