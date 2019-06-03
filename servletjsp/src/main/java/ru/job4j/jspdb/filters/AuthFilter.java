package ru.job4j.jspdb.filters;

import ru.job4j.jspdb.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("AUTH FILTER!");
        HttpServletRequest req = ((HttpServletRequest) servletRequest);
        HttpServletResponse resp = ((HttpServletResponse) servletResponse);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession(false);

        if (session != null || req.getRequestURI().contains("/userauth")) {
            filterChain.doFilter(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath().concat("/userauth"));
    }

    @Override
    public void destroy() {

    }
}
