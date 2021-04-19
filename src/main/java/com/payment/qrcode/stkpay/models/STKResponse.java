package com.payment.qrcode.stkpay.models;

import lombok.Data;

@Data
public class STKResponse {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResponseCode;      
    private String ResultDesc;          
    private String ResponseDescription;
    private int ResultCode;
}
