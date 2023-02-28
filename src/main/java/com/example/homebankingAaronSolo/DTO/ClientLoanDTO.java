package com.example.homebankingAaronSolo.DTO;

import com.example.homebankingAaronSolo.models.ClientLoan;

import java.util.List;

public class ClientLoanDTO {

    private long id;

    private String clientLoanName;

    private double clientLoanAmount;


    private int clientLoanPayments;



    public ClientLoanDTO(){};

    public ClientLoanDTO(ClientLoan clientLoan){

        this.id=clientLoan.getId();

        this.clientLoanName = clientLoan.getName();

        this.clientLoanAmount = clientLoan.getAmount();

        this.clientLoanPayments = clientLoan.getPayments();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientLoanName() {
        return clientLoanName;
    }

    public void setClientLoanName(String clientLoanName) {
        this.clientLoanName = clientLoanName;
    }

    public double getClientLoanAmount() {
        return clientLoanAmount;
    }

    public void setClientLoanAmount(double clientLoanAmount) {
        this.clientLoanAmount = clientLoanAmount;
    }

    public int getClientLoanPayments() {
        return clientLoanPayments;
    }

    public void setClientLoanPayments(int clientLoanPayments) {
        this.clientLoanPayments = clientLoanPayments;
    }
}

