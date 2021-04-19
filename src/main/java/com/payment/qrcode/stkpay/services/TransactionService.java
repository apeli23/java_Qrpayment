package com.payment.qrcode.stkpay.services;

// import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.qrcode.stkpay.repositories.TransactionRepository;

import java.util.Date;
import java.util.UUID;

import com.payment.qrcode.stkpay.dtos.TransactionRequest;
import com.payment.qrcode.stkpay.models.Transaction;

@Service
public class TransactionService {

    // @Autowired
    // private ModelMapper modelMapper;


    @Autowired
    private  TransactionRepository transactionRepository;
    

    public String create(TransactionRequest trans) {


        UUID uuid = UUID.randomUUID();

        Transaction transaction = new Transaction();
        transaction.setCreatedAt(new Date());
        transaction.setAmount(trans.getAmount());
        transaction.setStatus("Recieved");
        transaction.setPhonenumber(trans.getPhonenumber());
        transaction.setTransactionID(uuid.toString());
        transactionRepository.save(transaction);

        return uuid.toString();
    }


    public void updateSent(){

    }

    public void updateComplete(){

    }
}
