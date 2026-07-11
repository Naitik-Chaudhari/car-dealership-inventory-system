package com.naitik.car_dealership_api.security;

import com.naitik.car_dealership_api.service.CustomUserDetailsService;
import com.naitik.car_dealership_api.service.JwtService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private JwtService jwtService;
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        customUserDetailsService = mock(CustomUserDetailsService.class);

        jwtAuthenticationFilter =
                new JwtAuthenticationFilter(jwtService, customUserDetailsService);
    }

    @Test
    void shouldContinueFilterChainWhenAuthorizationHeaderIsMissing()
            throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain chain = mock(FilterChain.class);

        jwtAuthenticationFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}