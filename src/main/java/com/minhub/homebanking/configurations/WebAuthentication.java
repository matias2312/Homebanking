package com.minhub.homebanking.configurations;

import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;




import java.util.List;
//antes de abrir el saco hace esto
@Configuration
// a través de nuestra clase web authentication, que hereda de GlobalAuthenticationConfigurerAdapter, le decimos a Spring Security cómo vamos a identificar al usuario autenticado y con qué roles nos vamos a manejar
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(inputName-> {

            Client client = clientRepository.findByEmail(inputName);

            if (client != null) {
            if(inputName.contains("admin@mindhub.com")){
                //estamos creando una session, con la persona o usuario que esta tratando de ingresar
                return new User(client.getEmail(), client.getPassword(),
                        AuthorityUtils.createAuthorityList("ADMIN"));
            } else {
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

