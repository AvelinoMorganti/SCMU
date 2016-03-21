package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DAO.DAOProperties;
import classes.Account;
import classes.MySQLAuthenticator;
import classes.Properties;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author avelino
 */
public class json extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Cookie[] cookies = request.getCookies();
        String passwordHasheada = null;
        String username = null;
        Account user;
        MySQLAuthenticator auth = new MySQLAuthenticator();
        boolean login = false;

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {

                if (cookies[i].getName().equals("username")) {
                    username = cookies[i].getValue().toString();

                }
                if (cookies[i].getName().equals("password")) {
                    passwordHasheada = cookies[i].getValue().toString();
                }
            }

            if ((username != null) && (passwordHasheada != null)) {
                user = auth.getAccount(username);

                if (user.getUsername().equals(username) && (user.getPassword().equals(passwordHasheada))) {
                    login = true;

                    DAOProperties daoP = new DAOProperties();
                    Properties p = daoP.getPropertiesByID(user.getIdProperties());

                    String json = new Gson().toJson(p);
                    response.getWriter().write(json);
                }
            }
        }

        if (login == false) {
            response.setStatus(401);
            response.sendError(401);
            //response.getWriter().write("\[\{\"access\":\"denied\"");
            //response.getWriter().write("401 Unauthorized");
            //response.getWriter().write("forbidden");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
