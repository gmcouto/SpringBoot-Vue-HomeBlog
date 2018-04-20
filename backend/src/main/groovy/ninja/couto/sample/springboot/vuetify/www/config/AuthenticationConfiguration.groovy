package ninja.couto.sample.springboot.vuetify.www.config

import ninja.couto.sample.springboot.vuetify.www.entities.Role
import ninja.couto.sample.springboot.vuetify.www.entities.User
import ninja.couto.sample.springboot.vuetify.www.repositories.PostRepository
import ninja.couto.sample.springboot.vuetify.www.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.NoOpPasswordEncoder

@Configuration
class AuthenticationConfiguration {
    @Bean
    NoOpPasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.instance
    }

    @Autowired
    UserRepository userRepository

    @Autowired PostRepository postRepository

    @Autowired
    void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        if(userRepository.findByUsername("john")==null)
            userRepository.save(new User("john","123", [new Role("USER")]))
        if(userRepository.findByUsername("writer")==null)
            userRepository.save(new User("writer","123", [new Role("USER"), new Role("WRITER")]))
        if(userRepository.findByUsername("admin")==null)
            userRepository.save(new User("admin","123", [new Role("USER"), new Role("WRITER"), new Role("ADMIN")]))
        builder.userDetailsService(userDetailsService(userRepository)).passwordEncoder(passwordEncoder())
    }

    private UserDetailsService userDetailsService(final  UserRepository repository) {
        new UserDetailsService(){
            @Override
            UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = repository.findByUsername(username);
                if(user==null)
                    throw new UsernameNotFoundException("Could not find "+username)
                return new CustomUserDetails(user);
            }
        }
    }
}
