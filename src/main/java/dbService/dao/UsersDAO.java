package dbService.dao;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Objects;

public class UsersDAO
{
    private Session session;
    private CriteriaBuilder builder;

    public UsersDAO(){} //important for hibernate

    public UsersDAO(Session session)
    {
        this.session = session;
        builder = session.getCriteriaBuilder();
    }

    public UsersDataSet getByName(String name) throws HibernateException
    {
        CriteriaQuery<UsersDataSet> criteriaQuery = builder.createQuery(UsersDataSet.class);
        criteriaQuery.from(UsersDataSet.class);
        List<UsersDataSet> udsList = session.createQuery(criteriaQuery).getResultList();
        for(UsersDataSet uds : udsList)
        {
            if (Objects.equals(uds.getName(), name))
            {
                session.close();
                return uds;
            }
        }
        throw new HibernateException(new Throwable());
    }

    public UsersDataSet getById(long id) throws HibernateException
    {
        CriteriaQuery<UsersDataSet> criteriaQuery = builder.createQuery(UsersDataSet.class);
        criteriaQuery.from(UsersDataSet.class);
        List<UsersDataSet> udsList = session.createQuery(criteriaQuery).getResultList();
        for(UsersDataSet uds : udsList)
        {
            if (Objects.equals(uds.getId(), id))
            {
                session.close();
                return uds;
            }
        }
        throw new HibernateException(new Throwable());
    }

    public long insertUser(String name, String password, String email)
    {
        long id = (long) session.save(new UsersDataSet(name,password,email));
        System.out.println("Added user with id: " + id);
        return id;
    }
    public long insertUser(String name, String password)
    {
        long id = (long) session.save(new UsersDataSet(name,password));
        System.out.println("Added user with id: " + id);
        return id;
    }
}
