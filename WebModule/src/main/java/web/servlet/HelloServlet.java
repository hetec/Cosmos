package web.servlet;

import domain.LocaleBeanLocal;
import service.DateBeanLocal;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by patrick on 03.02.16.
 */
@WebServlet(urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet{

    @EJB
    DateBeanLocal dateBean;
    @EJB
    LocaleBeanLocal localeBean;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        final PrintWriter out = response.getWriter();
        out.print("Hello it's " + dateBean.getCurrentDateString()
                + " in " + localeBean.getLocaleString());
    }
}
