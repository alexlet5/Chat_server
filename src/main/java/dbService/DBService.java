package dbService;

import dbService.dataSets.UsersDataSet;

public interface DBService
{

    public UsersDataSet getUserByName(String name) throws DBException;

    public UsersDataSet getUserById(long id) throws DBException;

    public long addUser(String name, String password, String email) throws DBException;

    public long addUser(String name, String password) throws DBException;


}
