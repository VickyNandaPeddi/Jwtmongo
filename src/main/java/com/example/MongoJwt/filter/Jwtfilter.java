package com.example.MongoJwt.filter;

import com.example.MongoJwt.service.CustomUserdetailsservice;
import com.example.MongoJwt.util.Jwtutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.SubstituteLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Jwtfilter extends OncePerRequestFilter {
    LoggerFactory loggerFactory;
    SubstituteLogger substLogger;
    Logger logger = loggerFactory.getLogger(Jwtfilter.class);
    @Autowired
    Jwtutil jwtutil;
    @Autowired
    private CustomUserdetailsservice customUserdetailsservice;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String userName = null;
        String authorizationHeader = request.getHeader("Authorization");
        logger.error("authorization header" + authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            logger.error("username from token" + token);

            try {
                userName = jwtutil.extractUsername(token);
                logger.error("Username from token :" + userName);
            } catch (Exception e) {
                System.out.println("unable to  get token   :" + e.getMessage());
            }

        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = customUserdetailsservice.loadUserByUsername(userName);
            if (jwtutil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
