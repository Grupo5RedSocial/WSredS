/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ws.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.samples.rs.webservices.ResponseManager;
import org.zkoss.zk.ui.Executions;

public class SessionFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        boolean allowedRequest = false;
        String url = request.getRequestURI();
        if (url.contains("error.jsp") || url.contains("/WsRed/login") || url.contains("/WsRed/addUser") || url.contains("/WsRed/changePass") || url.contains("/WsRed/forgetPass")) {
            allowedRequest = true;
        }
        if (!allowedRequest) {
        	String h=request.getHeader(ResponseManager.cookieName);
            String value=ResponseManager.validaToken(request.getHeader(ResponseManager.cookieName));
            if(!"OK".equals(value)){
            	PrintWriter out=response.getWriter();
            	out.println(value);
            	out.close();
            	response.sendRedirect("login_effect.zul");
            }
            else{
            	chain.doFilter(req, res);
            }
        }
        else{
        	chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
