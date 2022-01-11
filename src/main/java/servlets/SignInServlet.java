package servlets;
import accounts.AccountService;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet
{
    private final AccountService accountService;

    public SignInServlet(AccountService accountService)
    {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        UsersDataSet currentUser = null;
        try
        {
            currentUser = accountService.getUserByLogin(login);
        }
        catch(DBException e)
        {
            System.out.println("Non-existent user account sign-in attempted!");
        }

        if (currentUser!=null && currentUser.getPassword().equals(pass))
        {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(200);
            accountService.addSession(request.getRequestedSessionId(),currentUser);
            //response.getWriter().println("Authorized: "+login);
            response.sendRedirect("/");

        }
        else
        {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(401);
            response.getWriter().printf("Unauthorized");
        }

    }
}
