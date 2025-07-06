package com.wechat.common.dto;

public class SeckillResponse {
    private String status;
    private String message;
    private String paymentPage;

    public SeckillResponse() {}

    public SeckillResponse(String status, String message, String paymentPage) {
        this.status = status;
        this.message = message;
        this.paymentPage = paymentPage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentPage() {
        return paymentPage;
    }

    public void setPaymentPage(String paymentPage) {
        this.paymentPage = paymentPage;
    }
}
