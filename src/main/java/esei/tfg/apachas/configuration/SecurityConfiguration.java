/*Clase que define la configuración básica deseguridad */
//@Configuration: identifica esta clase como parte de la configuración
//@EnableWebSecurity: permite activar el módulo de seguridad
//@Override: indica que es un método sobreescrito

/*AUTHENTIFICATION AND AUTHORIZATION*/
/* AUTHENTIFICATION: who you are in the context of an application (HTTP, FORMS, CERTIFICATE, TOKENS)
   AUTHORIZATION: what you are allowed to do in the context of an application (PRIVILEGES/AUTHORITIES, ROLES)
*/

/*HTTP BASIC AUTHENTICATION*/
/*El proceso del navegador de pedir un usuario y una contraseña cuando realiza una petición para autentificar a un usuario
Es un sistema simple que transmite las credenciales sin encriptar. Se codifican en el sistema BASE64 en el tránsito.
Por lo tanto, es necesario complementarlo con SSL (HTTPS) para proporcionar confidencialidad
HTTP + SSL proporciona la seguridad mínima para aplicaciones que trabajan con información no sensibles
No proporciona log out
    CLIENT                              SERVER
       |                                   |
       | --------- GET/HOME ----------->   |
       | <--- 401 UNAUTHORIZED ---------   |
       | -- GET/HOME AUTHORIZATION ---->   |
       | <--------- 200 OK -------------   |
 */

/*HTTPS*/
/*Proporciona seguridad en la información del usuario entre los endpoints.
HTTP por si solo no es seguro porque solo codifica
HTTP + SSL es seguro porque encripta
Los certificados SSL pueden ser:
    - SELF SIGNED: utiles durante el desarrollo pero no proporcionan confianza al usuario final
    - SIGNED BY TRUSTED AUTHORITY (Comodo, Symantic, DigiCert,...): Son firmas de entidades de confianza, resulta util durante la producción

    CLIENT                                                              SERVER
       | --------------------- HTTPS REQUEST -------------------------->   |
       | <----------- SERVER SENDS CERTIFICATE WITH PUBLIC KEY --------    |
       | ----- SSL VERIFICATION IF OK BROWSER SENDS BACK SESSION KEY -->   |
*/

/*FORM BASED AUTHENTICATION*/
/*
It is the process of authenticating a user by presenting a custom HTML page that will collect credentials and by directing the authentication
responsibility to the web application that collects the form data.

LOGIN
------

CLIENT                                                          SERVER (SESSION ID)
   |   ---------------------- GET/HOME ----------------------->         |
   |   <----- IF NOT AUTHENTICATED REDIRECT TO LOGIN PAGE -----         |
   |   -------- POST FORM DATA (USERNAME + PASSWORD) --------->         |
   |   <-- IF OK CREATE SESSION ID AND RETURN AUTH COOKIE -----         |
   |   ------------- GET/REPORTS ALL (COOKIE) ---------------->         |
   |   <--------------------- 200 OK --------------------------         |


LOGOUT
------

CLIENT                                                          SERVER (NO SESSION ID)
   |   ------------ GET/LOGOUT OR EXPIRES (INACTIVITY) ------->         |
   |   <----- SESSION IS INVALIDATED REDIRECT FOLLOGIN --------         |


This application is responsible for dealing with form data and performing the actual authorization pathern.
It is the most widespread form of authentication, well suited for self- contained apps.
The user credentials are conveyed in the clear to the web application, so use SSL to keep them safe transit.
 */

/*JWT*/
/*
jwt authentication -> compact and safe way to transmit data between two parties. The information can be trusted because it is digitally signed.
jwt structure -> header(alg,typ) + payload(data) + signature (secret)

LOGIN
-----

CLIENT(OUTSIDE YOUR APP DOMAIN)                                                         AUTH SERVER
                                ---- USER SIGN IN (CRENTIALS, FB, GOOGLE....) ------>
                                <--- AUTHENTICATED TOKEN JWT CREATED AND RETURNED --
                                                                                        APPLICATION (REST)
                                --- GET/REPORTS/ALL HEADER AUTHORIZATION BEARER ---->
                                <---------------- 200 OK ----------------------------
                                ------ POST/PRODUCT HEADER AUTHORIZATION BEARER ---->
                                <--------------- 200 OK -----------------------------



*/

package esei.tfg.apachas.configuration;

import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private SUserPrincipalDetails SUserPrincipalDetails;
    @Autowired
    private RUser rUser;

    public SecurityConfiguration(SUserPrincipalDetails SUserPrincipalDetails) {
        this.SUserPrincipalDetails = SUserPrincipalDetails;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConf = new CorsConfiguration();
        corsConf.addExposedHeader("Authorization");
        corsConf.addAllowedOrigin("*");
        corsConf.addAllowedMethod("GET");
        corsConf.addAllowedMethod("POST");
        corsConf.addAllowedMethod("PUT");
        corsConf.addAllowedMethod("DELETE");
        corsConf.addAllowedMethod("OPTIONS");
        corsConf.applyPermitDefaultValues();
        http
                .cors().configurationSource(request ->corsConf)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.rUser))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/usersUsers").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/groups").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/usersEvents").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/groupsUsers").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/events").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/groups").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/groupsUsers").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/usersEvents").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/events").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/usersUsers").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/events").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/usersEvents").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/usersEvents").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.SUserPrincipalDetails);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}