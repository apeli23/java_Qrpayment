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
import com.payment.qrcode.stkpay.services.TransactionService;


@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping(value = "/make/order", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrgenQRCode(@RequestBody TransactionRequest transaction) throws Exception {
        String trxid = transactionService.create(transaction);
        String barcode = AppConstants.BASE_URL +"/"+trxid;
        return okResponse(QRGen.generateQRCodeImage(barcode));
    }
    
    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
