<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
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
