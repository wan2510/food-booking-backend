package com.app.food_booking_backend.model.entity.enums;

public enum InvoiceStatus {
    CHUA_THANH_TOAN("Chưa thanh toán"),
    DA_THANH_TOAN("Đã thanh toán"),
    DANG_XU_LY("Đang xử lý"),
    DA_HUY("Đã hủy");

    private final String value;

    InvoiceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static InvoiceStatus fromValue(String value) {
        for (InvoiceStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid InvoiceStatus value: " + value);
    }
}