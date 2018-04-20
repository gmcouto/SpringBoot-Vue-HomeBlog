package ninja.couto.sample.springboot.vuetify.www.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity(debug = true)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    AuthenticationManager myAuthenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //we only provide resources
        http.authorizeRequests().anyRequest().denyAll();
    }

}