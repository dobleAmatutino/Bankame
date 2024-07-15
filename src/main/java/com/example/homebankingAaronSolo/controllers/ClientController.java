package com.example.homebankingAaronSolo.controllers;

import com.example.homebankingAaronSolo.DTO.ClientDTO;
import com.example.homebankingAaronSolo.models.Account;
import com.example.homebankingAaronSolo.models.Client;
import com.example.homebankingAaronSolo.repositories.AccountRepository;
import com.example.homebankingAaronSolo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){

        return new  ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    @RequestMapping("/api/clients")
    public List<ClientDTO> getClient() {

        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @RequestMapping("/clients/{id}")

    public ClientDTO getClientById(@PathVariable Long id){

        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @RequestMapping(path="/api/clients",method = RequestMethod.POST)
    public ResponseEntity<Object> registerAclient( @RequestParam String firstName, @RequestParam String lastName,

                                                   @RequestParam String email, @RequestParam String password){

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }


        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

       Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account newAccountregistered = new Account("VIN"+getRandomNumber(10000000,100000000), LocalDateTime.now(),0.00,newClient);

        clientRepository.save(newClient);
        accountRepository.save(newAccountregistered);

        return new ResponseEntity<>("BIENVENIDO A BANCA "+ newClient.getFirstName() ,HttpStatus.CREATED);

    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
