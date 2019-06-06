<%@page import="download.FileListProvider"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("files", FileListProvider.getFiles(getServletContext()));
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Загрузка файлов</h1>
        <form method="post" action="upload.jsp" enctype="multipart/form-data">
            <input type="file" name="file">
            <input type="submit" value="Загрузить">
        </form><br>
        <ul>
            <c:forEach var="file" items="${files}">
                <li><a href="<c:url value="download.jsp?filename=${file.name}"/>">Загрузить <c:out value="${file.name}" escapeXml="true"/></a></li>
            </c:forEach> 
        </ul>
    </body>
</html>
