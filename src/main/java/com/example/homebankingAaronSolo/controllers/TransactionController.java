package com.example.homebankingAaronSolo.controllers;

import com.example.homebankingAaronSolo.models.Account;
import com.example.homebankingAaronSolo.models.Client;
import com.example.homebankingAaronSolo.models.Transaction;
import com.example.homebankingAaronSolo.models.TransactionType;
import com.example.homebankingAaronSolo.repositories.AccountRepository;
import com.example.homebankingAaronSolo.repositories.ClientRepository;
import com.example.homebankingAaronSolo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private ClientRepository clientRepository;


    @Transactional
    @RequestMapping(path="/api/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> makeAtransaction(@RequestParam Double amount, @RequestParam String description,
                                                   @RequestParam String originAccount, @RequestParam String destinyAccount,
                                                   Authentication authentication){
        if(amount==null || description.isEmpty() || originAccount.isEmpty()
                || destinyAccount.isEmpty()){
            return new ResponseEntity<>("the information is not complete", HttpStatus.FORBIDDEN);
        }



        if(originAccount.equals(destinyAccount)){
            return new ResponseEntity<>("please the origin account can not be equal to destiny acount",
                    HttpStatus.FORBIDDEN);
        }

        Account originAccount1 = accountRepository.findByNumber(originAccount);

        if (amount> originAccount1.getBalance()){
            return new ResponseEntity<>("you have not that money for to transfer", HttpStatus.FORBIDDEN);
        }

        if (amount<=0){
            return new ResponseEntity<>("you cant transfer negative amounts",HttpStatus.FORBIDDEN);
        }


        Account destinyAccount1 = accountRepository.findByNumber(destinyAccount);
        if(originAccount1==null){
            return new ResponseEntity<>("this origin account does not exist",HttpStatus.FORBIDDEN);
        }

        Client currentClient = clientRepository.findByEmail(authentication.getName());

        if(!currentClient.getAccounts().contains(originAccount1)){
            return new ResponseEntity<>("this account does not belong you",HttpStatus.FORBIDDEN);
        }

        if (destinyAccount1==null){

            return new ResponseEntity<>("this destiny account does not exist",HttpStatus.FORBIDDEN);
        }

        Transaction originTransaction= new Transaction(TransactionType.DEBIT,-amount,
                "DEBIT TO ACCOUNT NUMBER"+destinyAccount1.getNumber(), LocalDateTime.now(),originAccount1);


        Transaction destinyTransaction =new Transaction(TransactionType.CREDIT,amount,
                "CREDIT FROM ACCOUNT NUMBER"+originAccount1.getNumber(), LocalDateTime.now(),destinyAccount1);

        originAccount1.setBalance(originAccount1.getBalance()+originTransaction.getAmount());
        destinyAccount1.setBalance(destinyAccount1.getBalance()+destinyTransaction.getAmount());

        transactionRepository.save(originTransaction);
        transactionRepository.save(destinyTransaction);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
