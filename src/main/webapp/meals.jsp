<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    tr.odd {color: green}
    tr.even {color: red}
</style>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">HOME</a> </h3>
<h2>List of meals</h2>

<table style="width:50%" border="1">
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? 'even' : 'odd'}">
            <td>${meal.id}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">edit</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
    <tr class="${meal.exceed ? 'even' : 'odd'}">
        <td><a href="meals?action=add">Add new</a></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>
</body>
</html>

