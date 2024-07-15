package com.example.homebankingAaronSolo.configurations;
import com.example.homebankingAaronSolo.models.Client;
import com.example.homebankingAaronSolo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableWebSecurity
@Configuration
public class WebAuthentications extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    MessageLog messageLog;
    @Autowired

    ClientRepository clientRepository;

    @Override

    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName -> {

            Client client = clientRepository.findByEmail(inputName);

            if (client != null && (client.getPassword().isEmpty()||client.getPassword()!= clientRepository.findByEmail(inputName).getPassword())){
                throw new UsernameNotFoundException(messageLog.invalidCredentials);

            }
            if(client==null){
                        throw new UsernameNotFoundException(messageLog.invalidCredentials);
            }

            if (client != null) {

                if (client.getEmail().contains("admin")) {

                    return new User(client.getEmail(), client.getPassword(),

                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
                else {
                    return new User(client.getEmail(), client.getPassword(),

                            AuthorityUtils.createAuthorityList("CLIENT"));



                }
            } else {

                throw new UsernameNotFoundException("Unknown user: " + inputName);

            }

        });

    }

    @Bean

    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }


}
