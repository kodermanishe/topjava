<%--
  Created by IntelliJ IDEA.
  User: koderman
  Date: 10/9/2018
  Time: 9:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<a href="meals">back</a>
<h2>${pageTitle}</h2>
<div></div>
<form action="meals" method="post">
    <input type="text" hidden="hidden" name="action" value="${action}">
    <input type="text" hidden="hidden" name="id" value="${id}">
    Date: <input type="datetime-local" name="dateTime" value="${dateTime}">
    Description: <input type="text" name="description" value="${description}">
    Calories: <input type="number" name="calories" value="${calories}">
    <button type="submit">${action}</button>
</form>
</div>

</body>
</html>
