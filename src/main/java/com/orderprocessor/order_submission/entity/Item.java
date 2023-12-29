package com.orderprocessor.order_submission.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Item {
    @NotEmpty(message = "item name should not be empty")
    String itemName;

    @NotNull(message = "item quantity should not be empty")
    Integer quantity;

    @NotNull(message = "item price should not be empty")
    Double price;    
}
