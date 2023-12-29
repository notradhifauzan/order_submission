package com.orderprocessor.order_submission.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Order {
    String orderId;

    Date orderDate;

    @Valid
    Customer customer;

    @Valid
    List<Item> items;

    double totalPrice;

    @Override
    public String toString()
    {
        String message = "";
        message += "Order details: \n\n";
        for(Item i:items)
        {
            message += i.getItemName() + " x" + i.getQuantity() + "\n";
        }

        message += "\nOrder ID: " + this.orderId + "\n";
        message += "Order Date: " + this.orderDate + "\n";
        message += "Total (RM): " + this.totalPrice + "\n\n";

        message += "Customer details\n";
        message += customer.getCustomerName() + "\n" + customer.getPhoneNumber() + "\n" + customer.getAddress() + "\n";
        
        return message;
    }

    public String getSubject()
    {
        String uniqueId = UUID.randomUUID().toString();
        String firstSix = uniqueId.substring(0, 6);

        return "ORDER #" + firstSix;
    }
}
