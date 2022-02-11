<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
is login：${sessionScope.finalLogin}<br/>

username：${sessionScope.finalUsername}<br/>

role：<br/>
${sessionScope.finalRole}
<br/>
<%=Arrays.toString((String[]) request.getSession().getAttribute("finalRole"))%>
<br/>
permission：${sessionScope.finalPermission}<br/>
<%=Arrays.toString((String[]) request.getSession().getAttribute("finalPermission"))%>
<br/>
</body>
</html>