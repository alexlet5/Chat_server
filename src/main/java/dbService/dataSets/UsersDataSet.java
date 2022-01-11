package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UsersDataSet implements Serializable
{
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique=true, updatable = false,nullable = false)
    private String name;

    @Column(name = "password", unique=true, updatable = false,nullable = false)
    private String password;

    @Column(name = "email", unique=true, updatable = false,nullable = true)
    private String email;

    public UsersDataSet() {}

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(long id, String name, String password)
    {
        this.setId(id);
        this.setName(name);
        this.setPassword(password);
    }

    public UsersDataSet(String name, String password, String email)
    {
        this.setId(-1);
        this.setName(name);
        this.setPassword(password);
        this.setEmail(email);
    }
    public UsersDataSet(String name, String password)
    {
        this.setId(-1);
        this.setName(name);
        this.setPassword(password);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "UserDataSet{" + "id= " + id + ", name='" + name +  '\'' + ", password = " + password + ", email = " + email + '}';
    }
}
