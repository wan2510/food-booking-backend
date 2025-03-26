package com.app.food_booking_backend.model.request;

import lombok.Data;

@Data
public class CreateInvoiceRequest {
    private String customer;
    private Double amount;
    private String date;
    private String type;
}