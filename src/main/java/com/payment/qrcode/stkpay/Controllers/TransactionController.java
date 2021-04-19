package com.payment.qrcode.stkpay.Controllers;


import java.awt.image.BufferedImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.payment.qrcode.stkpay.tools.QRGen;


@RestController
public class TransactionController {


    @PostMapping(value = "/qrgen/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrgenQRCode(@RequestBody String barcode) throws Exception {
        return okResponse(QRGen.generateQRCodeImage(barcode));
    }
    
    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
