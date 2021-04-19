package com.payment.qrcode.stkpay.repositories;
import java.util.List;

import com.payment.qrcode.stkpay.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByClientTransactionID(String clientTransactionID);

    List<Transaction> findByTransactionID(String transactionID);
    
}
