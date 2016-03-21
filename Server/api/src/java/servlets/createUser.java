package servlets;

import classes.Account;
import classes.MySQLAuthenticator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * should only work for “root”
 */
public class createUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String username = request.getParameter("username").toLowerCase();

        if (username.equals("")
                || password.equals("")
                || password2.equals("")) {
            response.sendRedirect("home.jsp?msg=Ocorreu um erro ao criar o usuário.");
        }

        if (session.getAttribute("username_s").equals("root")) {
            if (password.equals(password2)) {

                Account account = new Account(username, password);
                MySQLAuthenticator my = new MySQLAuthenticator();

                /*if (my.createAccount(account)) {
                    response.sendRedirect("home.jsp?msg=Usuário cadastrado com sucesso!");
                } else {
                    response.sendRedirect("home.jsp?msg=Ocorreu um erro ao cadastrar o usuário.");
                }*/

            } else {
                response.sendRedirect("home.jsp?msg=Ocorreu um erro ao cadastrar o usuário.");
            }
        } else {
            response.sendRedirect("home.jsp?msg=Ocorreu um erro ao cadastrar o usuário.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
