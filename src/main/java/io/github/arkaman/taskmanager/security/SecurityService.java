package io.github.arkaman.taskmanager.security;

import io.github.arkaman.taskmanager.domain.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public AppUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AppUser user) {
            return user;
        }

        throw new IllegalStateException("Invalid authentication principal");
    }
}
