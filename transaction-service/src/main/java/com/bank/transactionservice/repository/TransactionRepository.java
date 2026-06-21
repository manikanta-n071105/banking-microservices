package com.bank.transactionservice.repository;

import com.bank.transactionservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByAccountId(Long accountId);

}
