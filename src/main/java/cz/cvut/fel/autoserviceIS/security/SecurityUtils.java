package cz.cvut.fel.autoserviceIS.security;

import cz.cvut.fel.autoserviceIS.model.AbstractUser;
import cz.cvut.fel.autoserviceIS.security.model.AuthenticationToken;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

public class SecurityUtils {
    public static AbstractUser getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        assert context != null;
        final UserDetailsImpl userDetails = (UserDetailsImpl) context.getAuthentication().getDetails();
        return userDetails.getUser();
    }

    public static UserDetailsImpl getCurrentUserDetails() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null && context.getAuthentication().getDetails() instanceof UserDetailsImpl)
            return (UserDetailsImpl) context.getAuthentication().getDetails();
        else return null;
    }

    public static AuthenticationToken setCurrentUser(UserDetailsImpl userDetails) {
        final AuthenticationToken token = new AuthenticationToken(userDetails.getAuthorities(), userDetails);
        token.setAuthenticated(true);
        final SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
        return token;
    }
}
