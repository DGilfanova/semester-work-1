<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 01.11.2021
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${rooms}" var="room">
    <div class="col-lg">
        <img src=${room.photoReference} width="500" height="300">
        <br><br><br><br>
    </div>
    <div class="col-lg">
        <h3>${room.name}</h3>
        <br><br>
        <h5>${room.description}</h5>
        <br>
        <h5 style="font-weight: 300"><i>Price: ${room.price}$ / day</i></h5>
    </div>
</c:forEach>
