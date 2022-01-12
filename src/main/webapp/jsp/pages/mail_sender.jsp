<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Java mail sender!</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/sendMail" method="post">
        <table>
            <tr>
                <td>Send to:</td>
                <td><label>
                    <input type="text" name="to" value="fakemail.fhtagn@gmail.com"/>
                </label></td>
            </tr>
            <tr>
                <td>Subject:</td>
                <td><label>
                    <input type="text" name="subject" value="Message send with JAVA"/>
                </label></td>
            </tr>
        </table>
        <hr/>
        <textarea type="text" name="body" rows="5"cols="45">Message text...</textarea>
        <br/><br/>
        <input type="submit" value="Send message!"/>
    </form>
</body>
</html>
