package ru.job4j.jspdb.filters;

import ru.job4j.jspdb.logic.ValidateService;
import ru.job4j.jspdb.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private HttpSession session = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        System.out.println("FILTER!");

        HttpServletRequest req = ((HttpServletRequest) servletRequest);
        HttpServletResponse resp = ((HttpServletResponse) servletResponse);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        ValidateService vs = ValidateService.getInstance();


        String login = req.getParameter("login");
        String password = req.getParameter("password");

        System.out.printf("Login: %s Password: %s\n", login, password);

        if (login == null && password == null && this.session == null) {
            System.out.println("no login and password or no session");
            req.getRequestDispatcher("/userAuth.jsp").forward(req, resp);
        }

        int loggedId = vs.findLoggedUser(login, password);

        if (loggedId != -1) {
            System.out.println("User founded!");
            this.session = req.getSession();
            User loggedUser = vs.findById(loggedId);
            session.setAttribute("loggedUser", loggedUser);
            session.setAttribute("validate", vs);
            String[] messages = {"", ""};
            session.setAttribute("markupMessage", messages);
            req.getRequestDispatcher("/userslist").forward(req,resp);
            filterChain.doFilter(req, resp);
        } else {
            System.out.println("Incorrect Password!");
            req.getRequestDispatcher("/userAuth.jsp").forward(req, resp);
        }


    }

    @Override
    public void destroy() {

    }
}
