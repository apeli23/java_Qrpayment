package com.payment.qrcode.stkpay.models;

import lombok.Data;

@Data
public class STKRequest {
    private String BusinessShortCode;
	private String Password; 
	private String Timestamp;
	private String TransactionType;
	private String Amount;
	private String PartyA; 
	private String PartyB;
	private String PhoneNumber;
	private String CallBackURL; 
	private String AccountReference;
	private String TransactionDesc;
    
}
