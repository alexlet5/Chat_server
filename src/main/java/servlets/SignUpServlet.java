package servlets;

import accounts.AccountService;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet
{
    private final AccountService accountService;

    public  SignUpServlet(AccountService accountService)
    {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        System.out.println("Request to register: " + request.getParameter("login") + " " + request.getParameter("password")+ " " + request.getParameter("email"));
        try
        {
            accountService.addNewUser(new UsersDataSet(login,pass,email));
        } catch (DBException e)
        {
            e.printStackTrace();
        }
        System.out.println("Registered!");
        response.sendRedirect("/");
    }
}
