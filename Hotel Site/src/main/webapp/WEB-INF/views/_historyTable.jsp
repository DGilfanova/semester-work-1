<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 05.11.2021
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test = "${not empty orders}">
    <table class="table">
        <thead class="thead-inverse">
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Price</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${orders}" var="ord" varStatus="loop">
            <tr>
                <th scope="row">${loop.index+1}</th>
                <td>${ord.suggestion.name}</td>
                <td>${ord.suggestion.price}$</td>
                <td>${ord.date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test = "${empty orders}">
    <div class="container">
        <h3 style="text-align: center">You don't have orders history yet!</h3>
        <br>
    </div>
</c:if>
