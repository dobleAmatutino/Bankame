package com.example.homebankingAaronSolo.DTO;

import com.example.homebankingAaronSolo.models.Account;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountsDTO {

    private long id;

    private String number;

    private LocalDateTime creationDate;

    private double balance;

    Set<TransactionDTO> transactionsDTO=new HashSet<>();

    public AccountsDTO(){};

    public AccountsDTO(Account account){
        this.id= account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactionsDTO=account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactionsDTO() {
        return transactionsDTO;
    }

    public void setTransactionsDTO(Set<TransactionDTO> transactionDTO) {
        this.transactionsDTO = transactionsDTO;
    }
}
