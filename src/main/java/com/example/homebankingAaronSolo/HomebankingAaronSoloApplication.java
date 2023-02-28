package com.example.homebankingAaronSolo;

import com.example.homebankingAaronSolo.models.*;
import com.example.homebankingAaronSolo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingAaronSoloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingAaronSoloApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean

	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
			return (args) -> {

				Client client1 = new Client("Aaron","Mujica","mmaaron.17@gmail.com",passwordEncoder.encode("Elbananerosoyio") );
				clientRepository.save(client1);

				Client client2 = new Client("Luis","Maestre","luisñao2@gmail.com",passwordEncoder.encode("masBienlokita"));
				clientRepository.save(client2);

				Client client3 = new Client("Luis","Maestre","luisñao2@admin.com",passwordEncoder.encode("masBienlokita"));
				clientRepository.save(client3);

				Account account1 = new Account("VIN001", LocalDateTime.now(),5000,client1);
				accountRepository.save(account1);

				Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1),7500,client1);
				accountRepository.save(account2);

				Account account3 = new Account("VIN003", LocalDateTime.now(),75000,client2);
				accountRepository.save(account3);

				Transaction transaction1 = new Transaction(TransactionType.DEBIT,3000,"Transaction 1",LocalDateTime.now(),account1);
				transactionRepository.save(transaction1);

				Transaction transaction2 = new Transaction(TransactionType.CREDIT,4000,"Transaction 2",LocalDateTime.now(),account1);
				transactionRepository.save(transaction2);

				Transaction transaction3 = new Transaction(TransactionType.CREDIT,4000,"Transaction 3",LocalDateTime.now(),account2);
				transactionRepository.save(transaction3);

				//listas de cuotas
				List<Integer> automovilePayments = List.of(6,12,24,36);
				List<Integer> hipotecaryPayments = List.of(12,24,36,48,60);
				List<Integer> pesonalPayments = List.of(6,12,24);


				Loan automovile= new Loan("AUTOMOVILE",300000,automovilePayments,1.5);
				loanRepository.save(automovile);

				Loan personal= new Loan("PERSONAL",100000,pesonalPayments,2.00);
				loanRepository.save(personal);

				Loan hipotecary= new Loan("HIPOTECARY",500000,hipotecaryPayments,1.5);
				loanRepository.save(hipotecary);

				ClientLoan clientLoan1 = new ClientLoan(40000,client1,automovile,36,automovile.getName());
				clientLoanRepository.save(clientLoan1);

				ClientLoan clientLoan2 = new ClientLoan(1000,client1,personal,12,personal.getName());
				clientLoanRepository.save(clientLoan2);

				Card card1= new Card(client1,CardType.DEBIT,CardColor.GOLD,"1111 1111 1111 1111",111,LocalDate.now().plusYears(5),LocalDate.now());
				cardRepository.save(card1);

				Card card2= new Card(client2,CardType.CREDIT,CardColor.TITANIUM,"2222 2222 2222 2222",222,LocalDate.now().plusYears(5),LocalDate.now());
				cardRepository.save(card2);

			};
	}
}
