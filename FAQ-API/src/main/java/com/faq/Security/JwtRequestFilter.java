package com.faq.Security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faq.Services.AccountService;
import com.faq.common.Exceptions.ApiException;
import com.faq.common.Exceptions.ApiException.ApiErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().matches("/api/accounts/(login)?");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader == null) {
            throw new ApiException(ApiErrorType.TOKEN_NOT_PROVIDED, JwtRequestFilter.class);
        }

        if (!requestTokenHeader.startsWith("Bearer ")) {
            throw new ApiException(ApiErrorType.TOKEN_DOES_NOT_BEGIN_WITH_BEARER, JwtRequestFilter.class);
        }

        String username = null;
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        try {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (ExpiredJwtException e) {
            throw new ApiException(ApiErrorType.TOKEN_IS_EXPIRED, JwtRequestFilter.class);
        } catch (Exception e) {
            throw new ApiException(ApiErrorType.TOKEN_IS_INVALID, JwtRequestFilter.class);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.accountService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails).equals(true)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
