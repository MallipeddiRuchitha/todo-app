package com.demo.todoapp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class OrganizationFilter extends BasicAuthenticationFilter {


    private String claimLink;

    public OrganizationFilter(AuthenticationManager authenticationManager, String claimLink) {
        super(authenticationManager);
        this.claimLink = claimLink;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            throw new ServletException("Invalid Request, No authorization");
        String token = authHeader.substring(authHeader.lastIndexOf(" ") + 1);
        DecodedJWT decodedJWT = JWT.decode(token);
        String userEmail = decodedJWT.getClaim(claimLink).asString();
        request.setAttribute("userEmail", userEmail);
        log.info(userEmail);
        chain.doFilter(request,response);
    }
}