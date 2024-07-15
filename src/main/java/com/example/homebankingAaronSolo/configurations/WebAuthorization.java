package com.example.homebankingAaronSolo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration

public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Autowired
    MessageLog messageLog;
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        //Si create es falso y la solicitud no tiene HttpSession válida, este método devuelve nulo.
        //usa logica booleana para explicar.
        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/manager").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/newLoan").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/logout").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/logout").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/web/get-loan.html").hasAuthority("CLIENT")

                .antMatchers("/web/get-loan.html").hasAuthority("CLIENT")
                .antMatchers("/web/make-transactions.html").hasAuthority("CLIENT")
                .antMatchers("/web/created-cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/web/account.html").hasAuthority("CLIENT")
                .antMatchers("/web/cards.html").hasAuthority("CLIENT")
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")

                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        // turn off checking for CSRF tokens

        http.csrf().disable();



        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> {
            clearAuthenticationAttributes(req);
            res.getWriter().write(messageLog.successCredentials);
        });

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write(messageLog.invalidCredentials);
        });
        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler((req,res,auth)->{res.getWriter().write(messageLog.logout);});



    }
}