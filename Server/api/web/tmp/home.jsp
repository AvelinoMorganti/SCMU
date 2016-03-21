<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="classes.Account"%>
<%@page import="DAO.DAOUsers"%>
<%@page import="classes.MySQLAuthenticator"%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <%@include file="util/header.jsp" %>
        <h1>Welcome <% out.print(session.getAttribute("username_s")); %>!</h1>

        <ul>
            <li>
                <a href="change.jsp">Change your password</a>
            </li>
            <%
                //create user and delete user (root is necessary)
                if (session.getAttribute("username_s").toString().equals("root")) {
                    out.print("<br/><li><a href=\"create.jsp\">Create a new user</a></li>");
                    out.print("<br/><li><a href=\"delete.jsp\">Delete user</a></li>");
                }
            %>
            <!-- <br/> -->
            <li>
                <a href="logout?Action=Logout">Logout</a>
            </li>
        </ul>
        <%
            if (session.getAttribute("username_s").toString().equals("root")) {
                out.print("<h2>Users in database:</h2>");

                DAOUsers dao = new DAOUsers();
                ArrayList<Account> all = dao.getAllAccounts();

                if (all != null) {
                    out.print("<table border=\"1\">"
                            + "<thead><tr>"
                            + "<td>username: </td>"
                            + "<td>Is locked?</td>"
                            + "<td>Lock / Unlock</td>"
                            + "<td>Delete a user?</td>"
                            + "</tr></thead>");

                    for (Account a : all) {
                        out.print("<tr>");

                        if (a.getUsername().equals("root")) {
                            out.print("<td><b>" + a.getUsername() + "</b> (You)</td>");

                            out.print("<td> - - - - - - - - - - </td>");
                            out.print("<td> - - - - - - - - - - </td>");
                            out.print("<td> - - - - - - - - - - </td>");
                        } else {
                            out.print("<td>" + a.getUsername() + "</td>");
                            if (a.isLocked()) {
                                out.print("<td>Yes</td>");
                                out.print("<td><a href=\"lock?username=" + a.getUsername() + "&locked=0\">Unlock</a></td>");
                            } else {
                                out.print("<td>No</td>");
                                out.print("<td><a href=\"lock?username=" + a.getUsername() + "&locked=1\">Lock</a></td>");
                            }
                            out.print("<td><a href=\"deleteUser?username=" + a.getUsername() + " \">delete</a></td>");
                        }

                        out.print("</tr>");
                    }
                    out.print("</table>");
                }

                String msg = request.getParameter("msg");
                if (msg != null) {
                    out.print("<br/>" + msg);
                }
            }
        %>
    </body>
</html>