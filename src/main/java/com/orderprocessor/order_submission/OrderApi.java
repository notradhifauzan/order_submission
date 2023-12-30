package com.orderprocessor.order_submission;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orderprocessor.order_submission.entity.Order;
import com.orderprocessor.order_submission.service.EmailSenderService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.io.IOException;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
@RequestMapping("/nasi_aj/api")
public class OrderApi {

    private EmailSenderService emailSenderService;

    @PostMapping(value = "/submitOrder")
    public ResponseEntity<?> submitOrder(@Valid @RequestPart("order") Order order,@RequestPart("file") MultipartFile file) throws MessagingException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("receipt should not be empty", HttpStatus.BAD_REQUEST);
        }

        emailSenderService.sendMailWithAttachment(order,file);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping(value = "/v2/submitOrder")
    public ResponseEntity<?> submitOrder(@Valid @RequestBody Order order) throws MessagingException, IOException {
        if (order.getOrderReceipt() == null) {
            return new ResponseEntity<>("receipt should not be empty", HttpStatus.BAD_REQUEST);
        }
        emailSenderService.sendMailWithAttachment(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
    
}
