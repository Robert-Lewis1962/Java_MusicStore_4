<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Franklin Music - Search Results</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
    <h1>Search Results</h1>

    <table>
        <tr>
            <th style="width:75px">Code</th>
            <th style="width:500px">Description</th>
            <th style="width:75px;text-align:right">Price</th>
            <th></th>
        </tr>

        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.code}</td>
                <td>${product.description}</td>
                <td style="text-align:right">${product.priceCurrencyFormat}</td>
                <td>
                    <form method="POST" action="cartEntries">
                        <input type="hidden" name="productCode" value="${product.code}"></input>
                        <input type="submit" value="Add To Cart"></input>
                    </form>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="3"></td>
            <td>
                <form action="cart.html" method="GET">
                    <input type="submit" value="View Cart"></input>
                </form>
            </td>
        </tr>

    </table>
</body>
</html>