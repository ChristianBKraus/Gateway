package jupiterpa.gateway.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jupiterpa.gateway.security.model.*;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final String hash_key = "youtube";
	private final String secret_key = "42";

    public String validateLogin(JwtUserLogin user) {
        if (!user.getKey().matches(secret_key)) {
      	  log.warn("Secret Key {} is invalid [{}]", user.getKey(), user.toString());
      	   return "Invalid Login";
        }
        if (!user.getRole().matches("admin") && !user.getRole().matches("user")) {
      	  log.warn("Role {} is unknown [{}]", user.getRole(), user.toString());
      	  return "Unknown Role " + user.getRole();
        }
        log.info("UserLogin of {} validated", user.getUserName() );
        return "";
      }
	
    public String toToken(JwtUserLogin user) {
    	// Generate JWT Token from User

        Claims claims = Jwts.claims()
                .setSubject(user.getUserName());
        claims.put("role", user.getRole());
        claims.put("issued", System.currentTimeMillis());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, hash_key)
                .compact();
    }
    
    public JwtUserDetails fromToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    	
    	// Retrieve Token
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();
    	    	
    	// Retrieve User from JWT-Token
        Claims body = null;
        String userName;
        String role;
        long issued;
        try {
            body = Jwts.parser()
                    .setSigningKey(hash_key)
                    .parseClaimsJws(token)
                    .getBody();

            userName = body.getSubject();
            role = (String) body.get("role");
            issued = (long) body.get("issued");
        }
        catch (Exception e) {
        	e.printStackTrace();
            log.error(e.toString());
            throw new RuntimeException("JWT Token is incorrect");
        }
        if ( (body == null) || 
             (!role.matches("admin") && !role.matches("user")) ) {
        	log.error("Token {}/{} is incorrect", userName, role );
            throw new RuntimeException("JWT Token is incorrect");
        }
        
        // Determine granted Authorities
        List<GrantedAuthority> grantedAuthorities = 
        	AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        
        Date date = new Date(issued);
        log.info("User {} with role {} authenticated (issued: {}", userName, role, date );
        
        return new JwtUserDetails(userName, token, grantedAuthorities);
    }
    
}
