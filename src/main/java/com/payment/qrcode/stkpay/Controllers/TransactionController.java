package com.payment.qrcode.stkpay.Controllers;


import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payment.qrcode.stkpay.tools.AppConstants;
import com.payment.qrcode.stkpay.tools.QRGen;
import com.payment.qrcode.stkpay.dtos.TransactionRequest;
import com.payment.qrcode.stkpay.models.Transaction;
import com.payment.qrcode.stkpay.services.MpesaTrxPush;
import com.payment.qrcode.stkpay.services.TransactionService;


@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    MpesaTrxPush mpesatrxPush;



    @PostMapping(value = "/make/order", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrgenQRCode(@RequestBody TransactionRequest transaction) throws Exception {
        String trxid = transactionService.create(transaction);
        String barcode = AppConstants.BASE_URL +"/"+trxid;
        System.out.println(barcode+" | qrgenQRCode() ");
        return okResponse(QRGen.generateQRCodeImage(barcode));
    }

    @GetMapping(value = "/complete/order/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> transactionComplete(@PathVariable String id) throws Exception {
        Transaction trx = transactionService.updateSent(id);
        String response = mpesatrxPush.sendMpesa(trx);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
