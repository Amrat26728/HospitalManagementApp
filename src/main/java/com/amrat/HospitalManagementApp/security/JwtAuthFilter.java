package com.amrat.HospitalManagementApp.security;

import com.amrat.HospitalManagementApp.entities.User;
import com.amrat.HospitalManagementApp.repositories.UserRepository;
import com.amrat.HospitalManagementApp.util.AuthUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    private final HandlerExceptionResolver handlerExceptionResolver;

    // register this filter in the WebSecurityConfig file
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String requestTokenHeader = request.getHeader("Authorization");
            System.out.println("inside jwtauthfilter");

            if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
                System.out.println("inside 2");
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];

            String username = authUtil.getUsernameFromToken(token);
            System.out.println("username:"+username);

            // username should not be null and security context should be null
            // every filter has to fill security context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = userRepository.findByUsername(username).orElseThrow();

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }


            filterChain.doFilter(request, response);
        } catch (Exception ex){
            // this sends exception to global exception handler
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
