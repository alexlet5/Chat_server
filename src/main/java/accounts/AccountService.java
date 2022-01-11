package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import java.util.HashMap;
import java.util.Map;

public class AccountService
{
    private final Map<String, UsersDataSet> sessionIdToProfile;
    private final DBService dbService;

    public AccountService(DBService dbService)
    {
        sessionIdToProfile = new HashMap<>();
        this.dbService = dbService;
    }

    public void addNewUser(UsersDataSet userProfile) throws DBException
    {
        dbService.addUser(userProfile.getName(), userProfile.getPassword(), userProfile.getEmail());
    }

    public UsersDataSet getUserByLogin(String name) throws DBException
    {
        return dbService.getUserByName(name);
    }

    public UsersDataSet getUserBySessionId(String sessionId)
    {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet userProfile)
    {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId)
    {
        sessionIdToProfile.remove(sessionId);
    }
}
