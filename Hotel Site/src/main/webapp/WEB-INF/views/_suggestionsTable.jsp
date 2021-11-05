<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 31.10.2021
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${suggestions}" var="sug">
    <div class="col-lg">
        <img src=${sug.photoRefer} width="500" height="300">
        <br><br><br><br>
    </div>
    <div class="col-lg">
        <h3>${sug.name}</h3>
        <br><br>
        <h5>${sug.description}</h5>
        <br>
        <h5 style="font-weight: 300"><i>Price: ${sug.price}$/hour</i></h5>
    </div>
</c:forEach>