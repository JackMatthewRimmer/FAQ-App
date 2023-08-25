package com.faq.Security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faq.common.Exceptions.ApiException;
import com.faq.common.Exceptions.ApiExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterExceptionHandler extends OncePerRequestFilter {

    @Autowired
    private ApiExceptionHandler apiExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ApiException ex) {
            apiExceptionHandler.handleFilterLevelException(ex, httpServletResponse);
        }
    }
}
