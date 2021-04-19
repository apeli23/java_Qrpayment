package com.payment.qrcode.stkpay.tools;



public class AppConstants{


    public final static String SHORTCODE = "174379"; 
    public final static String CONSUMER_SECRET = "psa3rMtGLbMPI9Jc";
    public final static String CONSUMER_KEY = "Jj8TrQstN3kxRrQx06Etge3UUK73bAmh";  
    public final static String TRANSACTION_TYPE = "CustomerPayBillOnline";
    public final static String TOKEN_URL = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
    public final static String PAYMENT_URL ="https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
    public final static String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public final static String CALLBACK_URL = "https://posthere.io/357a-4151-af8b";
    public final static String BASE_URL="http://192.168.100.17:8080/complete/order";
}