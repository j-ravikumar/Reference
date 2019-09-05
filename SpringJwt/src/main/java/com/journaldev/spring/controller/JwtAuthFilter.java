package com.journaldev.spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	System.out.println("Inside Filter");
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        System.out.println(servletRequest.getRequestURI());
        if(servletRequest.getRequestURI().indexOf("/rest/auth") < 0) {
	        String authorization = servletRequest.getHeader("Authorization");
	        //authorization = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSYXZpa3VtYXIiLCJleHAiOjE1NjY4MjQ0OTIsImlhdCI6MTU2NjIxOTY5Mn0.FWtJySND6HaRDmR41m2UFbF19DdZx5tUmlYsANz8GzFukXjrP0aqPQ15Ksq5Ivn5R2tvRUsIUSNPcn4-Fl2wug";
	        if (authorization != null) {
	            JwtAuthToken token = new JwtAuthToken(authorization.replaceAll("Bearer ", ""));
	            SecurityContextHolder.getContext().setAuthentication(token);
	        } 
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
