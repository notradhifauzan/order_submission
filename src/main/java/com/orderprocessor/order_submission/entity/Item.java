package com.orderprocessor.order_submission.entity;

import lombok.Data;

@Data
public class Item {
    String itemName;
    int quantity;
    double price;    
}
