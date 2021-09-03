package com.bibliotheque.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With,Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials, authorization");
        response.addHeader("Access-Control-Allow-Methods","GET, POST, PATCH, PUT, DELETE");
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else if(request.getRequestURI().equals("/login")){
            chain.doFilter(request, response); return;
        }
        else{
            String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING);
            if(jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
                chain.doFilter(request, response); return;
            }

            ///This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims= Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(jwtToken.replace(SecurityConstants.TOKEN_PREFIX,""))
                    .getBody();
            String username = claims.getSubject();
            ArrayList<Map<String,String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(
                    r->{
                        authorities.add(new SimpleGrantedAuthority(r.get("authority")));
                    }
            );

            /*Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(jwtToken.substring(SecurityConstants.TOKEN_PREFIX.length()));
            String username = jwt.getSubject();
            List<String> roles = jwt.getClaim("roles").asList(String.class);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(
                    r->{
                        authorities.add(new SimpleGrantedAuthority(r));
                    }
            );*/

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        }
    }
}
