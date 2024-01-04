package com.orderprocessor.order_submission;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orderprocessor.order_submission.entity.Order;
import com.orderprocessor.order_submission.service.EmailSenderService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/nasi_aj/api")
public class OrderApi {

    private EmailSenderService emailSenderService;

    // @Operation(summary = "Create an order (Ver.1)", description = "This endpoint
    // receives 2 most critical data which is order details in json format and file
    // attachment. To use this endpoint, make sure you include
    // 'enctype=\"multipart/form-data\"' in your form tag. key:'order' value:{the
    // json container order details}, key:'file' value:{customer attachment}")
    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "400", description = "Bad request, some fields
    // are missing or attachment is missing", content = @Content(schema =
    // @Schema(implementation = ErrorResponse.class))),
    // @ApiResponse(responseCode = "201", description = "Successful creation of
    // order", content = @Content(schema = @Schema(implementation = Order.class))),
    // })
    @PostMapping(value = "/submitOrder")
    public ResponseEntity<?> submitOrder(@Valid @RequestPart("order") Order order,
            @RequestPart("file") MultipartFile file) throws MessagingException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("receipt should not be empty", HttpStatus.BAD_REQUEST);
        }

        order.setOrderId(getUniqueOrderId());

        emailSenderService.sendMailWithAttachment(order, file);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping(value = "/test")
    public String test()
    {
        return "mailing service is ready";
    }

    // @Operation(summary = "Create an order (ver.2)", description = "This endpoint
    // serves the same purpose as ver.1 except, in the order details, you have to
    // include a 'orderReceipt' field to store Base64 byte array (you need to
    // convert *using js* the attachment uploaded by the customer to get this
    // value). By using this endpoint, you only have to send the json value.")
    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "400", description = "Bad request, some fields
    // are missing.", content = @Content(schema = @Schema(implementation =
    // ErrorResponse.class))),
    // @ApiResponse(responseCode = "201", description = "Successful creation of
    // order.", content = @Content(schema = @Schema(implementation = Order.class))),
    // })
    @PostMapping(value = "/v2/submitOrder")
    public ResponseEntity<?> submitOrder(@Valid @RequestBody Order order) throws MessagingException, IOException {
        if (order.getOrderReceipt() == null) {
            return new ResponseEntity<>("receipt should not be empty", HttpStatus.BAD_REQUEST);
        }
        order.setOrderId(getUniqueOrderId());
        emailSenderService.sendMailWithAttachment(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    static String getUniqueOrderId() {
        String uniqueId = UUID.randomUUID().toString();
        return uniqueId.substring(0, 6).toUpperCase();
    }
}
