package com.orderprocessor.order_submission.entity;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Order {
    String orderId;
    Date orderDate;
    String orderType; //new
    @Valid
    Customer customer;
    @Valid
    List<Item> items;
    double totalPrice;
    byte[] orderReceipt;
    String fileType;

    @Override
    public String toString()
    {
        String message = "";
        message += "Order details: \n--------------------\n";
        for(Item i:items)
        {
            message += i.getItemName() + " x" + i.getQuantity() + " RM" + i.getPrice()*i.getQuantity() + "\n";
        }

        message += "\nOrder ID: " + this.orderId + "\n";
        message += "Order Date: " + this.orderDate + "\n";
        message += "Order Type: " + this.orderType + "\n";
        message += "Total (RM): " + this.totalPrice + "\n\n";

        message += "Customer details\n--------------------\n";
        message += customer.getCustomerName() + "\n" + customer.getPhoneNumber() + "\n" + customer.getAddress() + "\n";
        
        return message;
    }
}
