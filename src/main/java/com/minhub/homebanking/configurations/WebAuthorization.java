package com.minhub.homebanking.configurations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
//antes de abrir el saco hace esto
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards/delete","/clients/current/accounts/delete").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/loan/create").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts","/api/clients/current/cards","/api/clients/current/transactions","/api/clients/current/loan","/api/clients/current/transactions/payments","/api/download","/api/transactions/filtered").hasAnyAuthority("CLIENT","ADMIN")
                //cuando trabajamos con sprint security limitamos el acceso, de esta foma vamos liberand//dar acceso a las rutas, en este caso a todos
                .antMatchers("/web/index.html","/web/index.js","/web/assets/style.css","/web/assets/**","/web/videos/**","/web/download").permitAll()

                .antMatchers("/rest/**","/h2-console","/clients/current").hasAuthority("ADMIN")

                .antMatchers("/web/**").hasAnyAuthority("CLIENT","ADMIN");




        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("pwd")
//es el controlador del fomulario login
                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}