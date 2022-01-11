package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBServiceImpl implements DBService
{
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private static String url;
    private final SessionFactory sessionFactory;
    private static final DBService DBServiceSingleton = new DBServiceImpl();

    private DBServiceImpl()
    {
        url = new StringBuilder().append("jdbc:mysql://").                    //db type
            append("localhost:").                       //host name
            append("3306/").                            //port
            append("authorization?").                      //db name
            append("user=root&").                  //login
            append("password=root&").             //password
            append("useSSL=false&").append("serverTimezone=UTC").toString();

        Configuration configuration = getMysqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
        System.out.println("Работаем");
        getEverything();
    }

    public static DBService getInstance()
    {
        //if(DBServiceSingleton == null) DBServiceSingleton = new DBServiceImpl();
        return DBServiceSingleton;
    }

    private void getEverything()
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println(transaction.getStatus());
        transaction.commit();
        session.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration)
    {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder(); //создаем какуюто хрень
        builder.applySettings(configuration.getProperties()); //суем в хрень проперти
        ServiceRegistry serviceRegistry = builder.build();      //строим из хрени регистр
        return configuration.buildSessionFactory(serviceRegistry); //строим из регистра сессию
    }

    /*public static Connection getMysqlConnection()
    {
        try
        {
            return DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }*/

    private Configuration getMysqlConfiguration()
    {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");  //8!!!!!!!
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", url);
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public UsersDataSet getUserByName(String name) throws DBException
    {
        try
        {
            Session session = sessionFactory.openSession();
            UsersDAO uDAO = new UsersDAO(session);
            UsersDataSet uds = uDAO.getByName(name);
            session.close();
            return uds;
        } catch (HibernateException e)
        {
            throw new DBException(e);
        }
    }

    public UsersDataSet getUserById(long id) throws DBException
    {
        try
        {
            Session session = sessionFactory.openSession();
            UsersDAO uDAO = new UsersDAO(session);
            UsersDataSet uds = uDAO.getById(id);
            session.close();
            return uds;
        } catch (HibernateException e)
        {
            throw new DBException(e);
        }
    }

    public long addUser(String name, String password, String email) throws DBException
    {
        try
        {
            Session session = sessionFactory.openSession();
            UsersDAO uDAO = new UsersDAO(session);
            Transaction transaction = session.beginTransaction();
            long id = uDAO.insertUser(name,password,email);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e)
        {
            throw new DBException(e);
        }
    }
    public long addUser(String name, String password) throws DBException
    {
        try
        {
            Session session = sessionFactory.openSession();
            UsersDAO uDAO = new UsersDAO(session);
            Transaction transaction = session.beginTransaction();
            long id = uDAO.insertUser(name,password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e)
        {
            throw new DBException(e);
        }
    }
}
