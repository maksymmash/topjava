<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border=1>
<%--    <colgroup>--%>
<%--        <col span="3" style="background:Khaki"><!-- С помощью этой конструкции задаем цвет фона для первых трех столбцов таблицы-->--%>
<%--        <col style="background-color:#2cff23">--%>
<%--        <col style="background-color:#ff564f">--%>
<%--    </colgroup>--%>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.isExcess() ? 'red' : 'green'}">
            <td><fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }" /></td>
            <td><c:out value="${meal.getDescription()}" /></td>
            <td><c:out value="${meal.getCalories()}" /></td>
            <td>Update</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>