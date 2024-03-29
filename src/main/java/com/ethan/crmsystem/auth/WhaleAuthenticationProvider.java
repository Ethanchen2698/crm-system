package com.ethan.crmsystem.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class WhaleAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        Authentication parent = super.createSuccessAuthentication(principal, authentication, user);

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                ((SecurityUser) user).getId(), parent.getCredentials(), parent.getAuthorities());

        result.setDetails(parent.getDetails());

        return result;
    }
}
