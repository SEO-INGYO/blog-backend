package com.example.backend.user.listener;

import com.example.backend.user.entity.UserRevision;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        UserRevision userRevision = (UserRevision) revisionEntity;

        // Get SecurityContext and check for null
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = (securityContext != null) ? securityContext.getAuthentication() : null;

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                userRevision.setUsername(username);
            } else {
                userRevision.setUsername(principal.toString());
            }
        } else {
            userRevision.setUsername("anonymousUser"); // Default value when no principal is available
        }

        userRevision.setTimestamp(System.currentTimeMillis());
    }
}