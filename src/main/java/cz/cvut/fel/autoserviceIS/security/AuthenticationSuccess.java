package cz.cvut.fel.autoserviceIS.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.autoserviceIS.security.model.LoginStatus;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("successHandler")
public class AuthenticationSuccess implements AuthenticationSuccessHandler, LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationSuccess.class);
    private final ObjectMapper mapper;

    @Autowired
    public AuthenticationSuccess(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    private String getUsername(Authentication authentication) {
        if (authentication == null) return "";
        return ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        final String username = getUsername(authentication);
        if (LOG.isTraceEnabled())
            LOG.trace("Successfully authenticated user {}", username);

        final LoginStatus loginStatus = new LoginStatus(true, username, null, authentication.isAuthenticated());
        mapper.writeValue(httpServletResponse.getOutputStream(), loginStatus);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (LOG.isTraceEnabled())
            LOG.trace("Successfully logged out user {}", getUsername(authentication));

        final LoginStatus loginStatus = new LoginStatus(false, null, null, true);
        mapper.writeValue(httpServletResponse.getOutputStream(), loginStatus);
    }
}
