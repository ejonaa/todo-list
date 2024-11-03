package com.crispy.crispy_be_challenge_ejona_aliaj.security;

import com.crispy.crispy_be_challenge_ejona_aliaj.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public final class SecurityUtil {

    public static String getUserName() {
        Authentication authentication = getAuthentication();
        if (Objects.nonNull(authentication)) {
            return authentication.getName();
        } else {
            throw new ApiException("Principal is not defined");
        }
    }

    private static Authentication getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication).orElseThrow(() -> new ApiException("Authentication not found"));
    }
}
