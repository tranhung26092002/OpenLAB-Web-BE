package vn.com.openlab.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("${api.prefix}/oauth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    @GetMapping
    public String welcome() {
        return "Welcome to Google!";
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        if (principal != null) {
            logger.info("Username: {}", principal.getName());
        } else {
            logger.info("No user is authenticated.");
        }
        return principal;
    }

    @GetMapping("/login")
    public String login() {
        return "Please login with Google";
    }
}
