package com.example.homebankingAaronSolo.DTO;

import com.example.homebankingAaronSolo.models.Account;
import com.example.homebankingAaronSolo.models.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private Account account;

    Set<AccountsDTO> accountsDTO = new HashSet<>();

    Set<ClientLoanDTO> loansDTO = new HashSet<>();

    Set<CardDTO> cardDTO = new HashSet<>();

    public ClientDTO(){}


    public ClientDTO(Client client){

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accountsDTO=client.getAccounts().stream().map(account1 -> new AccountsDTO(account1)).collect(Collectors.toSet());
        this.loansDTO = client.getLoans().stream().map(clientLoan->new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.cardDTO =client.getCards().stream().map(card->new CardDTO(card)).collect(Collectors.toSet());
    }
// no me esta mostrando las cuentas en el json


    public Set<CardDTO> getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(Set<CardDTO> cardDTO) {
        this.cardDTO = cardDTO;
    }

    public Set<ClientLoanDTO> getLoansDTO() {
        return loansDTO;
    }

    public void setLoansDTO(Set<ClientLoanDTO> loansDTO) {
        this.loansDTO = loansDTO;
    }

    public Set<AccountsDTO> getAccountsDTO() {
        return accountsDTO;
    }

    public void setAccountsDTO(Set<AccountsDTO> accountsDTO) {
        this.accountsDTO = accountsDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
