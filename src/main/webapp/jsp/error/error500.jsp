<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error 500</title>
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
<a href="${pageContext.request.contextPath}/controller?command=command=go_home_command">Home</a>
</body>
</html>
