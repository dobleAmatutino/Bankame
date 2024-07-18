package com.example.homebankingAaronSolo.controllers;

import com.example.homebankingAaronSolo.DTO.AccountsDTO;
import com.example.homebankingAaronSolo.DTO.ClientDTO;
import com.example.homebankingAaronSolo.models.Account;
import com.example.homebankingAaronSolo.models.Client;
import com.example.homebankingAaronSolo.models.Transaction;
import com.example.homebankingAaronSolo.repositories.AccountRepository;
import com.example.homebankingAaronSolo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;




    @RequestMapping("/api/accounts")
    public List<AccountsDTO> getAccount(){
        return accountRepository.findAll().stream().map(account -> new AccountsDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping(path = "/api/accounts/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        return accountRepository.findById(id)
                .map(account -> new ResponseEntity<>(new AccountsDTO(account), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(value = "/api/account/{id}",method = RequestMethod.PATCH)
    public ResponseEntity<Object> deleteAccount(@PathVariable @RequestParam Long id){
        Account accountTodelete = accountRepository.findById(id).orElse(null);

        if (accountTodelete==null){
            return new ResponseEntity<>("this account doesn't exist",HttpStatus.BAD_GATEWAY);
        }
      //  Transaction transaction = accountTodelete.getTransactions();
      //  if(ac


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path="/api/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAaccount(Authentication authentication){

        Client currentClient= clientRepository.findByEmail(authentication.getName());

        if (currentClient.getAccounts().size()>=3){
            return new ResponseEntity<>("you have more accounts than allowed", HttpStatus.FORBIDDEN);
        }

        else {
            Account newAccount = new Account("VIN"+getRandomNumber(10000000,100000000), LocalDateTime.now(),0.00,currentClient);
            accountRepository.save(newAccount);

            return new ResponseEntity<>("ACCOUNT CREATED", HttpStatus.CREATED);
        }

    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
