package com.payment.qrcode.stkpay.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import com.payment.qrcode.stkpay.models.Transaction;
import com.payment.qrcode.stkpay.repositories.TransactionRepository;
import com.payment.qrcode.stkpay.tools.AppConstants;
import com.payment.qrcode.stkpay.tools.Utils;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MpesaTrxPush {


    @Autowired
    private  TransactionRepository transactionRepository;


    public String sendMpesa(Transaction trx) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        JSONObject job = new JSONObject();
        
        job.put("BusinessShortCode", AppConstants.SHORTCODE);
        job.put("Password", genPassword(AppConstants.SHORTCODE, AppConstants.PASSKEY, dateFormat.format(date)));
        job.put("Timestamp", dateFormat.format(date));
        job.put("TransactionType", AppConstants.TRANSACTION_TYPE);
        job.put("Amount", trx.getAmount()+"");
        job.put("PartyA", trx.getPhonenumber());
        job.put("PartyB", AppConstants.SHORTCODE);
        job.put("PhoneNumber", trx.getPhonenumber());
        job.put("CallBackURL", AppConstants.CALLBACK_URL);
        job.put("AccountReference", dateFormat.format(date));
        job.put("TransactionDesc", "Order Pay");


        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer "+getToken());

        headers.put("Content-Type", "application/json");


        String response = "";

        try {
            response = Utils.postRequest(AppConstants.PAYMENT_URL, job.toString(), headers);
            System.out.println(response + " | sendMpesa() ");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
        processResponse(new JSONObject(response), trx);


        return response;

    }

    private String genPassword(String shortcode, String passkey, String timeStamp) {
        String value = shortcode + passkey + timeStamp;
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    private String getToken() {

        String auth = AppConstants.CONSUMER_KEY + ":" + AppConstants.CONSUMER_SECRET;

        String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.ISO_8859_1));
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", authHeader);

        String response = "";

        try {
            response = Utils.getRequest(AppConstants.TOKEN_URL, headers);
            System.out.println(response+" | genPassword() ");
        } catch (ClientProtocolException e) {

            e.printStackTrace();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        JSONObject resp = new JSONObject(response);

        return resp.getString("access_token");

    }

    private void processResponse(JSONObject job, Transaction trx){

        int respCode =  job.getInt("ResponseCode");
        if (respCode != 0){
            trx.setStatus("Failed");
            transactionRepository.save(trx);
            return;
        }

        String CheckoutRequestID = job.getString("CheckoutRequestID");
        trx.setClientTransactionID(CheckoutRequestID);
        trx.setStatus("submitted");
        transactionRepository.save(trx);

    }
}
