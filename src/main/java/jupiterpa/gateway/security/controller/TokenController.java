package jupiterpa.gateway.security.controller;

import jupiterpa.gateway.security.model.JwtUserLogin;
import jupiterpa.gateway.security.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
	
    @Autowired private JwtService jwtService;

    public TokenController() { }

    @PostMapping
    public String generate(@RequestBody final JwtUserLogin user) {
        String error = jwtService.validateLogin(user);
        if (!error.matches("") ) {
        	throw new LoginException(error);
        }
        return jwtService.toToken(user);
    }
    
}
