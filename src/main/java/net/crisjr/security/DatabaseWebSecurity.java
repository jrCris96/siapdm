package net.crisjr.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public UserDetailsManager userCustom(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery(
            "SELECT id_usuario, password, CASE WHEN estado = 'habilitado' THEN 1 ELSE 0 END AS enabled " +
            "FROM usuario WHERE id_usuario = ?"
        );
        users.setAuthoritiesByUsernameQuery(
            "SELECT id_usuario, authority FROM user_authorities WHERE id_usuario = ?"
        );
        return users;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf 
                .ignoringRequestMatchers("/api/dialogflow/fulfillment") // CSRF desactivado solo para el webhook
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/dialogflow/fulfillment").permitAll() // Solo este endpoint es público
                // El resto de tus reglas aquí
                .requestMatchers("/tinymce/**", "/icons/**", "/css/**", "/bcrypt/**", "/icons2/**").permitAll()
                .requestMatchers("/home").hasAnyAuthority("SUPER_ADMIN")
                .requestMatchers("/usuarios/view/**").permitAll()
                .requestMatchers("/usuarios/**").hasAnyAuthority("SUPER_ADMIN")
                .requestMatchers("/vehiculos/**").hasAnyAuthority("SUPER_ADMIN")
                .requestMatchers("/mesa/**").hasAnyAuthority("SUPER_ADMIN")
                .requestMatchers("/jefes/**").hasAnyAuthority("SUPER_ADMIN")
                .requestMatchers("/amonestaciones/**").hasAnyAuthority("SUPER_ADMIN")
                .requestMatchers("/aportes/**").hasAuthority("Hacienda")
                .requestMatchers("/retiros/**").hasAuthority("Hacienda")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.loginPage("/login").successHandler(successHandler).permitAll())
            .logout(logout -> logout.permitAll());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
