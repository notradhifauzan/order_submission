package com.orderprocessor.order_submission.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Customer {
    @NotEmpty(message = "customer name should not be empty")
    String customerName;
    @NotEmpty(message = "customer phone should not be empty")
    String phoneNumber;
    @NotEmpty(message = "customer address should not be empty")
    String address;
}
