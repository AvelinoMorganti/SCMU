package interfaces;

import classes.Account;
import classes.Account;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAuthenticator {

    public Account getAccount(String name);

    void createAccount(String name, String pwd1, String pwd2);

    boolean createAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(String name);

    void logout(Account acc);

    Account login(String name, String pwd);

    Account login(HttpServletRequest req, HttpServletResponse resp);

    void change_pwd(String name, String pwd1, String pwd2);
}
