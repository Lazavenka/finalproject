<%--
  Created by IntelliJ IDEA.
  User: Roger
  Date: 16.01.2022
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Error 404</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} failed!
<hr/>
Exception - ${pageContext.exception}
<hr/>
Status - ${pageContext.errorData.statusCode}
<hr/>
Servlet name - ${pageContext.errorData.servletName}
<hr/>
<a href="${pageContext.request.contextPath}/controller?command=go_about_page_command">Home</a>
</body>
</html>
