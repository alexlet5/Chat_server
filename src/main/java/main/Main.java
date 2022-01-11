package main;

import accounts.AccountService;
import dbService.DBService;
import dbService.DBServiceImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ChatWsServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(80);

        DBService dbService = DBServiceImpl.getInstance();
        AccountService accountService = new AccountService(dbService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new ChatWsServlet(accountService)), "/");

        ResourceHandler resource_public = new ResourceHandler();
        resource_public.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_public, context});
        server.setHandler(handlers);

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }
}
