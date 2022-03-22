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
is login：${sessionScope.final_isLogin}<br/>
username：${sessionScope.final_loginUsername}<br/>
role：<br/>
${sessionScope.final_hasRoles}
<br/>
<%=Arrays.toString((String[]) request.getSession().getAttribute("final_hasRoles"))%>
<br/>

</body>
</html>