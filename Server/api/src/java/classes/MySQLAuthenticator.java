package classes;

import interfaces.IAuthenticator;
import DAO.MySQLConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class MySQLAuthenticator {

    /*
     Recebe um nome e se existir um usuario com este nome no banco, 
     retorna um objeto Account vinculado a ele
     */
    public Account getAccount(String name) {

        Account account = null;

        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement("SELECT "
                            + "idaccount, "
                            + "username, "
                            + "password, "
                            + "salt, "
                            + "locked, "
                            + "properties_idproperties "
                            + "FROM account "
                            + "WHERE username = ?");

            getUser.setString(1, name);

            ResultSet data = getUser.executeQuery();

            long tmpIdaccount;
            String tmpUsername;
            String tmpPassword;
            String tmpSalt;
            boolean tmpLocked;
            long tmpProperties;

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpIdaccount = data.getLong(1);
                tmpUsername = data.getString(2);
                tmpPassword = data.getString(3);
                tmpSalt = data.getString(4);
                tmpLocked = data.getBoolean(5);
                tmpProperties = data.getLong(6);

                conn.closeConnection();

                account = new Account(tmpIdaccount, tmpUsername, tmpPassword, tmpSalt, tmpLocked, tmpProperties);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());
        }
        return account;
    }

    /*    
     Recebe um objeto Account, salva no banco.
     */
    public boolean createAccount(Account account, int idProperties) {
        if (account != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO account VALUES(?,?,?,?,?,?)");

                insert.setInt(1, 0);
                insert.setString(2, account.getUsername());
                insert.setString(3, account.getPassword());
                insert.setString(4, account.getSalt());
                insert.setString(5, "0");
                insert.setInt(6, idProperties);

                insert.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                //JOptionPane.showMessageDialog(null, "Erro:" + ex.getLocalizedMessage() + "\n\n" + ex.getMessage());
                Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /*    
     Recebe um objeto Account, atualiza no banco.
     */
    public boolean updateAccount(Account account) {
        if (account != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE account "
                                + "SET password = ?, salt = ? "
                                + "WHERE username = ?");

                update.setString(1, account.getPassword());
                update.setString(2, account.getSalt());
                update.setString(3, account.getUsername());
                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /*
     Recebe uma String nome e apaga os dados vinculado 
     a uma conta com este nome, se essa conta existir.
     */
    public boolean deleteAccount(String name) {
        if ((name != null) && (!name.equals(""))) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                String sql = "DELETE FROM `account` WHERE `username` = '" + name + "'";
                conn.getStatement().execute(sql);

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());
                //Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public void logout(Account acc) {
        acc.setLoggedIn(false);
    }

    public Account login(String name, String pwd) {

        Account acc = getAccount(name);

        try {
            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().
                    prepareStatement("SELECT password, salt, locked "
                            + "FROM account WHERE username = ?");

            ResultSet data = getUser.executeQuery();

            if (data == null || !data.next()) {
                return null;
            }

            String securePassword = data.getString(1);
            String salt = data.getString(2);
            boolean locked = data.getBoolean(3);

            conn.closeConnection();

            String newSecurePassword = hashPassword.getSHA512(pwd + salt);

            if (!newSecurePassword.equals(securePassword)) {
                return null;
            }

            acc.setLoggedIn(true);
            acc.setLocked(locked);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return acc;
    }

    public void createAccount(String name, String pwd1, String pwd2) {
        if (pwd1.equals(pwd2)) {
            Account acc = new Account(name, pwd1);
            //createAccount(acc);
        }
    }

    public Account login(HttpServletRequest req, HttpServletResponse resp) {
        Account acc = login(req.getParameter("username"),
                req.getParameter("password"));
        if (acc != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", req.getParameter("username"));
            session.setAttribute("password", req.getParameter("password"));
        }
        return acc;
    }

    public void change_pwd(String name, String pwd1, String pwd2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
