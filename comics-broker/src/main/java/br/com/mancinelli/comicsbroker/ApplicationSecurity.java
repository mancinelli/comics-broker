package br.com.mancinelli.comicsbroker;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(); 
    	//return NoOpPasswordEncoder.getInstance();
    }
	
 	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**").permitAll()
            	//.antMatchers("/app/**").authenticated()
				.antMatchers("/**").permitAll();
        http
			.authorizeRequests()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .successForwardUrl("/app")
                .permitAll()
                .and()
            .logout()
            	.deleteCookies("remember-me")
                .permitAll()
                .logoutSuccessUrl("/login")
                .and()
            .rememberMe();
    }
    
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.passwordEncoder(passwordEncoder())
			.rolePrefix("ROLE_")
			.dataSource(dataSource)
    		.usersByUsernameQuery("SELECT usr_email AS username, usr_password AS password, usr_enabled AS enabled FROM usr WHERE usr_email=?")
    		.authoritiesByUsernameQuery("SELECT usr_email AS username, usrr_name AS role_name FROM usr JOIN usra ON (usr_uuid = usra_user) JOIN usrr ON (usra_role = usrr_id) WHERE usr_email=?");
	}

}