package com.payment.qrcode.stkpay.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    
    private String amount;


    private String phonenumber;
    
}

