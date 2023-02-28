package com.example.homebankingAaronSolo.DTO;

import com.example.homebankingAaronSolo.models.Loan;

import java.util.List;

public class LoanDTO {

    private Double interests;
    private Long id;

    private String name;

    private List<Integer> payments;

    private Double maxAmount;

    public LoanDTO(){}

    public LoanDTO(Loan loan){

        this.id = loan.getId();
        this.name = loan.getName();
        this.payments=loan.getPayments();
        this.maxAmount= loan.getMaxAmount();
        this.interests = loan.getInterests();
    }

    public Double getInterests() {
        return interests;
    }

    public void setInterests(Double interests) {
        this.interests = interests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }
}
