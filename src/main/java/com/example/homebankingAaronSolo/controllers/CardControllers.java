package com.example.homebankingAaronSolo.controllers;

import com.example.homebankingAaronSolo.DTO.CardDTO;
import com.example.homebankingAaronSolo.models.Card;
import com.example.homebankingAaronSolo.models.CardColor;
import com.example.homebankingAaronSolo.models.CardType;
import com.example.homebankingAaronSolo.models.Client;
import com.example.homebankingAaronSolo.repositories.CardRepository;
import com.example.homebankingAaronSolo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CardControllers {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping("api/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(cards->new CardDTO(cards)).collect(Collectors.toList());
    }

    @RequestMapping("api/cards/{id}")
    public  CardDTO getCardsByid(@PathVariable Long id){
       return cardRepository.findById(id).map(card->new CardDTO(card)).orElse(null);

    }

    @RequestMapping(path="/api/clients/current/cards",method = RequestMethod.POST)
    public ResponseEntity<Object> createAcard( @RequestParam CardType type,
                                              @RequestParam CardColor color, Authentication authentication) {
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        System.out.println("All cards: " + currentClient.getCards());

        // Filtrar tarjetas de débito
        Set<Card> debitCards = currentClient.getCards().stream()
                .filter(card -> card.getType().equals(CardType.DEBIT))
                .collect(Collectors.toSet());

        // Filtrar tarjetas de crédito
        Set<Card> creditCards = currentClient.getCards().stream()
                .filter(card -> card.getType().equals(CardType.CREDIT))
                .collect(Collectors.toSet());

        // Imprimir resultados para depuración
        System.out.println("Debit cards: " + debitCards);
        System.out.println("Credit cards: " + creditCards);
        if (type == null) {
            return new ResponseEntity<>("put the cardtype", HttpStatus.FORBIDDEN);
        }
        if (color == null) {
            return new ResponseEntity<>("put the color that you want", HttpStatus.FORBIDDEN);

        }

        if(debitCards.size()>= 3 ){
            return new ResponseEntity<>("exceded limit debit of cards",HttpStatus.FORBIDDEN);
        }

        else if (creditCards.size()>=3){
            return new ResponseEntity<>("exceded limit credit of cards",HttpStatus.FORBIDDEN);
        }
        else {

            Card newCard = new Card(currentClient, type, color,
                    getRandomNumber(1000, 10000) + " " + getRandomNumber(1000, 10000) + " " +
                            getRandomNumber(1000, 10000) + " " + getRandomNumber(1000, 10000),
                    getRandomNumber(100, 1000), LocalDate.now().plusYears(5), LocalDate.now());

            cardRepository.save(newCard);

            return new ResponseEntity<>("Card created", HttpStatus.CREATED);


        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

