package com.payment.qrcode.stkpay.repositories;
import com.payment.qrcode.stkpay.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
}
