package com.raul.trabalhoJwt.core.security;

import com.raul.trabalhoJwt.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;


        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            var token = this.recoverToken(request);
            if (token != null) {
                try {
                    var subject = tokenService.validateToken(token);
                    if (subject != null) {
                        UserDetails user = userRepository.findByLogin(subject);
                        if (user != null) {
                            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {

                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("User not found");
                            return;
                        }
                    }
                } catch (Exception e) {

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid or expired token");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }
        private String recoverToken(HttpServletRequest request) {
            var authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                return null;
            }
            return authHeader.replace("Bearer ", "");
        }
    }
