package cz.cvut.fel.autoserviceIS.security.service.model;

import cz.cvut.fel.autoserviceIS.model.AbstractUser;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {
    private AbstractUser user;
    private final Set<GrantedAuthority> authorities;

    public UserDetailsImpl(AbstractUser user) {
        Objects.requireNonNull(user);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
    }

    public UserDetailsImpl(AbstractUser user, Collection<GrantedAuthority> authorities) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(authorities);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
        this.authorities.addAll(authorities);
    }

    private void addUserRole() {
        authorities.add(new SimpleGrantedAuthority(user.getAccessType().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AbstractUser getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public AccessType getRole(){
        return user.getAccessType();
    }
}
