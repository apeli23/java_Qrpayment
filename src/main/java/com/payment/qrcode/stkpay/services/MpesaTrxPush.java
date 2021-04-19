package com.payment.qrcode.stkpay.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import com.payment.qrcode.stkpay.models.STKRequest;
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


    public String sendMpesa(Double amount, long phone, Transaction trx) {
        STKRequest req = new STKRequest();
        DateFormat dateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");
        Date date = new Date();

        req.setAmount(amount);
        req.setAccountReference(trx.getTransactionID());
        req.setBusinessShortCode(AppConstants.SHORTCODE);
        req.setPartyA(phone);
        req.setPartyB(AppConstants.SHORTCODE);
        req.setTimestamp(dateFormat.format(date));
        req.setTransactionType(AppConstants.TRANSACTION_TYPE);
        req.setPassword(genPassword(AppConstants.SHORTCODE, AppConstants.PASSKEY, dateFormat.format(date)));
        req.setPhoneNumber(phone);
        req.setCallBackURL(AppConstants.CALLBACK_URL);
        req.setTransactionDesc("Order Pay");

        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer "+getToken());

        headers.put("Content-Type", "application/json");

        JSONObject jo = new JSONObject(req);

        String response = "";

        try {
            response = Utils.postRequest(AppConstants.PAYMENT_URL, jo.toString(), headers);
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

    private String genPassword(long shortcode, String passkey, String timeStamp) {
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
