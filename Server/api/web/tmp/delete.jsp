<% //response.sendRedirect("home.jsp");%>

<%-- 
    Document   : home
    Created on : 08/10/2015, 14:58:48
--%>
<%
    //Se não houver sessão, redireciona para index.
    if (session.getAttribute("username_s") != null) {
        //if (session.getAttribute("username_s") != "root") {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete User</title>
    </head>
    <body>
        <form name="deleteform" action="deleteUser" method="POST">
            <%
                if (session.getAttribute("username_s").equals("root")) {
            %>
            User to exclude: <input type="text" name="username" id="username"  maxlength="35" size=10 value="" required="" title="Digite o nome do usuario a excluir"/>
            <br/>
            Root password: <input type="password" name="password" id="password"  maxlength="35" size=10 value="" required="" title="Digite sua senha"/>
            <input type="submit" name="Action" id="Action" value="Delete" title="Delete a user"/>
            <%
                } else {
                    out.print("<h1>Forbidden! =/</h1>");
                }
            %>
        </form>
    </body>
</html>
<%    } else {
        response.sendRedirect("index.jsp");
    }
%>