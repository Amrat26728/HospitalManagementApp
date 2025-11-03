package com.amrat.HospitalManagementApp.util;

import com.amrat.HospitalManagementApp.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserInfo {

    public User currentUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()){
            throw new IllegalStateException("No authenticated user found!");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user){
            return user;
        }else{
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }
    }

}
