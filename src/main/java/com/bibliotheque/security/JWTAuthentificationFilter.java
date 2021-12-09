package com.bibliotheque.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager){
        super();
        this.authenticationManager = authenticationManager;
    }
// cette methode pour recuperer username et password dans un objet
// authentication et lenvoi a spring pour que ce dernier
// appelle userdetailsservice et voir si l'utilisateur existe si'il
// existe spring il verifie le mdp
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser user = null;

        try{
            // deserialization de json en objet AppUser
            user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
    }
// build jwt apres que spring a verifier le mdp
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User springUser = (User)authResult.getPrincipal();
        String jwtToken = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .claim("roles", springUser.getAuthorities())
                .compact();
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwtToken);


        /*User user = (User)authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(
                a->{
                    roles.add(a.getAuthority());
                }
        );
        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET));
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);*/
    }
}
