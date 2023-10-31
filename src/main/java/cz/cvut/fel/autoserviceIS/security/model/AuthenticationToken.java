package cz.cvut.fel.autoserviceIS.security.model;

import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken implements Principal {

    private UserDetailsImpl userDetails;

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, UserDetailsImpl userDetails) {
        super(authorities);
        this.userDetails = userDetails;
        super.setAuthenticated(true);
        super.setDetails(userDetails);
    }

    @Override
    public String getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public UserDetailsImpl getPrincipal() {
        return userDetails;
    }
}