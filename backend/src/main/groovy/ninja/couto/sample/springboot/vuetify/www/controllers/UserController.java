package ninja.couto.sample.springboot.vuetify.www.controllers;

import ninja.couto.sample.springboot.vuetify.www.entities.Role;
import ninja.couto.sample.springboot.vuetify.www.entities.User;
import ninja.couto.sample.springboot.vuetify.www.pojos.UserRegistration;
import ninja.couto.sample.springboot.vuetify.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;


    @PreAuthorize("isAnonymous()")
    @PostMapping
    public String register(@RequestBody UserRegistration userRegistration){
        if(!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
            return "Error the two passwords do not match";
        else if(userService.getUser(userRegistration.getUsername()) != null)
            return "Error this username already exists";

        //Checking for non alphanumerical characters in the username.
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        if(pattern.matcher(userRegistration.getUsername()).find())
            return "No special characters are allowed in the username";

        userService.save(new User(userRegistration.getUsername(), userRegistration.getPassword(), Arrays.asList(new Role("USER"))));
        return "User created";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<User> users(){
        return userService.getAllUsers();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/logout")
    public void logout(@RequestParam (value = "access_token") String accessToken){
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value ="/name")
    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
