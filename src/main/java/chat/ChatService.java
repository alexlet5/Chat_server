package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService
{
    private Set<ChatWebSocket> users;
    public ChatService()
    {
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(String data)
    {
        for(ChatWebSocket user : users)
        {
            user.sendString(data);
        }
    }

    public void add(ChatWebSocket webSocket)
    {
        users.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket)
    {
        users.remove(webSocket);
    }
}
