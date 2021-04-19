package com.payment.qrcode.stkpay.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String phonenumber;


    private Date createdAt;

    private Double amount;

    private String status;

    private String transactionID;

    private String clientTransactionID;
    
}
