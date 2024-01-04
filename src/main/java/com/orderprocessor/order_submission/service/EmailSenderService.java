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

    final String MAIL_TO = "nurshafikah3103@gmail.com";
    final String MAIL_FROM = "nasiajdenaialam@gmail.com";

    public void sendMailWithAttachment(Order order, MultipartFile file) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(MAIL_FROM);
        mimeMessageHelper.setTo(MAIL_TO);
        mimeMessageHelper.setText(order.toString());
        mimeMessageHelper.setSubject("[NOTIFICATION] NASI AJ ORDER#" + order.getOrderId());

        // Attach the file to the email
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
            mimeMessageHelper.addAttachment(file.getOriginalFilename(), byteArrayResource);
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

        mimeMessageHelper.setFrom(MAIL_FROM);
        mimeMessageHelper.setTo(MAIL_TO);
        mimeMessageHelper.setText(order.toString());
        mimeMessageHelper.setSubject("[NOTIFICATION] NASI AJ ORDER#" + order.getOrderId());
        ByteArrayResource byteArrayResource = new ByteArrayResource(order.getOrderReceipt());
        mimeMessageHelper.addAttachment("order_receipt#"+order.getOrderId()+"."+order.getFileType(), byteArrayResource, getContentType(order.getFileType()));

        javaMailSender.send(mimeMessage);

        System.out.println("mail with attachment sent successfully");
    }

    static String getContentType(String fileType) {
        switch (fileType.toLowerCase()) {
            case "jpg":
                return "image/jpg";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "pdf":
                return "application/pdf";
            default:
                return "application/octet-stream";
        }
    }
}
