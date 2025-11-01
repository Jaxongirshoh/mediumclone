package dev.wisespirit.mediumclone.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wisespirit.mediumclone.model.auth.UserSessionData;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || authorization.isBlank()){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authorization.substring(7);
        if (!jwtUtil.isValid(token)){
            filterChain.doFilter(request,response);
            return;
        }
        Claims claims = jwtUtil.getClaims(token);
        if (claims.get("token")==null||claims.get(token).equals("REFRESH")){
            filterChain.doFilter(request,response);
            return;
        }
        String email = jwtUtil.getEmail(token);
        CustomUserDetails userDetails =(CustomUserDetails) userDetailsService.loadUserByUsername(email);
        UserSessionData userSessionData = new UserSessionData(
                userDetails.geId(),
                userDetails.getUsername()
        );

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userSessionData,null,userDetails.getAuthorities());
        WebAuthenticationDetails webAuthDetails =
                new WebAuthenticationDetails(request);
        authToken.setDetails(webAuthDetails);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
