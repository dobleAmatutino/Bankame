package com.example.homebankingAaronSolo.controllers;

import com.example.homebankingAaronSolo.DTO.LoanApplicationDTO;
import com.example.homebankingAaronSolo.DTO.LoanDTO;
import com.example.homebankingAaronSolo.models.*;
import com.example.homebankingAaronSolo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoansController {


    @Autowired
    private TransactionRepository transactionRepository;


    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/api/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(loan->new LoanDTO(loan)).collect(Collectors.toList());
    }

    @RequestMapping(path = "/api/loans/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getLoansByid(@PathVariable Long id){
            return loanRepository.findById(id).map(loan -> new ResponseEntity<>(new LoanDTO(loan),HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(path="/api/newLoan",method = RequestMethod.POST)
    public ResponseEntity<Object> createAloan(@RequestParam String loanName,
                                              @RequestParam List<Integer> loanPayments,
                                              @RequestParam Double loanMaxAmount,
                                              @RequestParam Double loanInterests){
        Loan newLoan=loanRepository.findByName(loanName);

        if(loanName.isEmpty() && loanPayments.isEmpty() && loanMaxAmount.toString().isEmpty()&& loanInterests.toString().isEmpty() ){
            return new ResponseEntity<>("all the parameters are empty",HttpStatus.FORBIDDEN);
        }

        if(loanName.isEmpty()==true){
            return new ResponseEntity<>("the new loan should have a name",HttpStatus.FORBIDDEN);
        }
        if(loanPayments.size()==1){
            if (loanPayments.get(0) <= 0 || loanPayments.get(0).toString().isEmpty()){
                return new ResponseEntity<>("the loan should have atleast 2 payments", HttpStatus.FORBIDDEN);
            }
        }
        else if (loanPayments.size()>1) {
            for (int i= 0; i<loanPayments.size();i++){

                if ( loanPayments.get(i).toString().isEmpty()==true || loanPayments.get(i).toString()==""||loanPayments.get(i)<=0) {
                    return new ResponseEntity<>("the loan should have atleast 2 payments", HttpStatus.FORBIDDEN);
                }
                else continue;
        }



        }

        if (loanMaxAmount.toString().isEmpty()==true||loanMaxAmount<=10000||loanMaxAmount==null){
            return new ResponseEntity<>("we dont have micro loans",HttpStatus.FORBIDDEN);
        }

        if (loanInterests<=0){
            return new ResponseEntity<>("the loan needs interests",HttpStatus.FORBIDDEN);
        }



        if(newLoan!=null){
            return new ResponseEntity<>("there is already a loan with this name",HttpStatus.FORBIDDEN);
        }
        else {
            Loan publicLoan = new Loan(loanName, loanMaxAmount, loanPayments,loanInterests);
            loanRepository.save(publicLoan);


            return new ResponseEntity<>("the loan is now available to customers", HttpStatus.CREATED);
        }
    }




    @Transactional
    @RequestMapping(path="/api/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> getAloan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication
                                           authentication) {
        Client currentClient = clientRepository.findByEmail(authentication.getName());

        if(loanApplicationDTO.getAmount()<=0){
            return new ResponseEntity<>("the amount is invalid",HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getId()<=0){
            return new ResponseEntity<>("Id loan invalid",HttpStatus.FORBIDDEN);
        }


        if(loanApplicationDTO.getDestinyAccount().isEmpty()){
            return new ResponseEntity<>("we need a destiny account",HttpStatus.FORBIDDEN);
        }

        Account destinyAccount = accountRepository.findByNumber(loanApplicationDTO.getDestinyAccount());


        Loan loan = loanRepository.findById(loanApplicationDTO.getId());

        if(loan==null){
            return new ResponseEntity<>("the loan does not exist",HttpStatus.FORBIDDEN);
        }





        if(loanApplicationDTO.getAmount()>loan.getMaxAmount()){
            return new ResponseEntity<>("the amount exceeds the max amount for this loan",HttpStatus.FORBIDDEN);
        }

        if(!currentClient.getAccounts().contains(destinyAccount)){
            return new ResponseEntity<>("the destiny account doesnt belong you",HttpStatus.FORBIDDEN);
        }


        Transaction transactionLoan = new Transaction(TransactionType.CREDIT,loanApplicationDTO.getAmount(),
                loan.getName()+"aproved",LocalDateTime.now(),destinyAccount);

        transactionRepository.save(transactionLoan);

        destinyAccount.setBalance(destinyAccount.getBalance()+loanApplicationDTO.getAmount());

        if(loan.getName().equals("HIPOTECARY")){
            loanApplicationDTO.setAmount(1.5*loanApplicationDTO.getAmount());
        }
        if(loan.getName().equals("AUTOMOVILE")){
            loanApplicationDTO.setAmount(2*loanApplicationDTO.getAmount());
        }
        if(loan.getName().equals("PERSONAL")){
            loanApplicationDTO.setAmount(3*loanApplicationDTO.getAmount());
        }

        List<ClientLoan> customerLoans = currentClient.getLoans().stream().filter(prestamo->prestamo.getLoan()==loan).collect(Collectors.toList());


        if (customerLoans.size()>=1){
            return new ResponseEntity<>("you had completed the limit for this loan",HttpStatus.FORBIDDEN);
        }



        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()*1+loan.getInterests(),currentClient,loan,loanApplicationDTO.getPayments(),loan.getName());
        clientLoanRepository.save(clientLoan);


        return new ResponseEntity<>("you had apliccated for a: "+loanRepository.findById(loanApplicationDTO.getId()).getName()+ "loan: remember pay ",HttpStatus.CREATED);
    }
}
