package cz.cvut.fel.autoserviceIS.security;


import cz.cvut.fel.autoserviceIS.security.model.AuthenticationToken;
import cz.cvut.fel.autoserviceIS.security.service.UserDetailsServiceImpl;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getPrincipal().toString();
        if (LOG.isDebugEnabled()) LOG.debug("Authenticating user {}", username);
        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        final String password = (String) authentication.getCredentials();
        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Provided credentials don't match.");
        System.out.println(userDetails.getUser().getAccessType().toString());
        return SecurityUtils.setCurrentUser(userDetails);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass) ||
                AuthenticationToken.class.isAssignableFrom(aClass);
    }
}

