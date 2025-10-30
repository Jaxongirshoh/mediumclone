package dev.wisespirit.mediumclone.security;

import dev.wisespirit.mediumclone.model.auth.UserSessionData;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    public UserSessionData requireSessionData(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            throw new BadCredentialsException("unauthorized");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserSessionData userSessionData){
            return userSessionData;
        }

        throw new BadCredentialsException("unauthorized");
    }

    public Long requireUserId(){
        return requireSessionData().id();
    }
}
