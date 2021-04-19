package com.payment.qrcode.stkpay.models;

import lombok.Data;

@Data
public class STKRequest {
    private long BusinessShortCode;
	private String Password; 
	private String Timestamp;
	private String TransactionType;
	private long Amount;
	private String PartyA; 
	private long PartyB;
	private long PhoneNumber;
	private String CallBackURL; 
	private String AccountReference;
	private String TransactionDesc;
    
}
