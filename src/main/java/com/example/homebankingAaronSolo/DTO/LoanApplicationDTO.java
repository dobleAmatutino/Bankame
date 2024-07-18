package com.example.homebankingAaronSolo.DTO;

public class LoanApplicationDTO {

    private long id;

    private double amount;

    private int payments;

    private String destinyAccount;



    public  LoanApplicationDTO(){}



    public LoanApplicationDTO(long id, Double amount, int payments, String destinyAccount){this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destinyAccount = destinyAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getDestinyAccount() {
        return destinyAccount;
    }

    public void setDestinyAccount(String destinyAccount) {
        this.destinyAccount = destinyAccount;
    }
}
