<% //response.sendRedirect("home.jsp");%>

<%-- 
    Document   : home
    Created on : 08/10/2015, 14:58:48
--%>
<%
    //Se não houver sessão, redireciona para index.
    if (session.getAttribute("username_s") != null) {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change password</title>
    </head>
    <body>
        <form name="changeform" action="changePassword" method="POST">
            <%
                out.print("<h5>Hello " + session.getAttribute("username")
                        + "! Change your password by typing below:</h5>");
            %>
            New password: <input type="password" name="password" id="password"  maxlength="35" size=10 value="" required="" title="Digite sua senha"/>
            <br/>
            Retype (confirmation): <input type="password" name="password2" id="password2"  maxlength="35" size=10 value="" required="" title="Redigite sua senha"/>
            <input type="hidden" value=redirect_url/>
            <br/>
            <input type="submit" name="Action" id="Action" value="Change" title="Change password"/>
            <%
                String msg = request.getParameter("msg");
                if (msg != null) {
                    out.print("<br/>" + msg);
                }

            %>
        </form>
    </body>
</html>
<%    } else {
        response.sendRedirect("index.jsp");
    }
%>