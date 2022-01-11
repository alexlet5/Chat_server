package servlets;

import accounts.AccountService;
import chat.ChatService;
import chat.ChatWebSocket;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class ChatWsServlet extends WebSocketServlet
{
    private final int TimeOut = 10;
    private final ChatService chatService = new ChatService();
    private final AccountService accountService;
    public ChatWsServlet(AccountService accountService)
    {
        this.accountService = accountService;
    }
    @Override
    public void configure(WebSocketServletFactory factory)
    {
        factory.getPolicy().setIdleTimeout(TimeUnit.MINUTES.toMillis(TimeOut));
        factory.setCreator((ServletUpgradeRequest req, ServletUpgradeResponse resp)->
        {
            String id = req.getHttpServletRequest().getSession().getId();
            if (accountService.getUserBySessionId(id) != null)
                return new ChatWebSocket(chatService,accountService.getUserBySessionId(id).getName());

            else return new ChatWebSocket(chatService, "Anon");
        });
    }

}
