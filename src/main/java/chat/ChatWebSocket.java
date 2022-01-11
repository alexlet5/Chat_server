package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class ChatWebSocket
{
    ChatService chat;
    Session session;
    String name;
    public ChatWebSocket(ChatService chat, String name)
    {
        this.chat = chat;
        this.name = name;
    }

    @OnWebSocketConnect //тут наверное аштиэмэл
    public void onOpen(Session session) throws IOException
    {
        chat.add(this);
        this.session = session;
        session.getRemote().sendString(name);
    }

    @OnWebSocketMessage
    public void onMessage(String data)
    {
        chat.sendMessage(data);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        chat.remove(this);
    }

    public void sendString(String data)
    {
        try
        {
            session.getRemote().sendString(data);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
