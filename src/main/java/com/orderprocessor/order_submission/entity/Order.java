package com.orderprocessor.order_submission.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Order {
    String orderId;
    Date orderDate;
    Customer customer;
    List<Item> items;
    double totalPrice;
}
