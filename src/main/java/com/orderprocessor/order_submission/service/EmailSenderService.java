package com.orderprocessor.order_submission.service;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orderprocessor.order_submission.entity.Order;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    final String MAIL_TO = "alsyathir@gmail.com";

    public void sendMailWithAttachment(Order order, MultipartFile file) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("not.radhifauzan@gmail.com");
        mimeMessageHelper.setTo(MAIL_TO);
        mimeMessageHelper.setText(order.toString());
        mimeMessageHelper.setSubject(order.getSubject());

        // Attach the file to the email
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
            mimeMessageHelper.addAttachment(file.getOriginalFilename(),byteArrayResource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to attach file to email", e);
        }


        javaMailSender.send(mimeMessage);

        System.out.println("mail with attachment sent successfully");
    }

    public void sendMailWithAttachment(Order order) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("not.radhifauzan@gmail.com");
        mimeMessageHelper.setTo(MAIL_TO);
        mimeMessageHelper.setText(order.toString());
        mimeMessageHelper.setSubject(order.getSubject());
        ByteArrayResource byteArrayResource = new ByteArrayResource(order.getOrderReceipt());
        mimeMessageHelper.addAttachment("order receipt",byteArrayResource);

        javaMailSender.send(mimeMessage);

        System.out.println("mail with attachment sent successfully");
    }
}
