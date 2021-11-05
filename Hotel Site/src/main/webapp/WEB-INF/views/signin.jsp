<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 21.10.2021
  Time: 8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardSimpleLayout title="Sign in">
    <img class="card-img-top" src="images/signin.jpg" style="height: 250px" alt="Hotel photo">
    <div class="card-body p-4 p-md-5">
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-3 px-md-2">Sign in</h3>

        <c:if test = "${not empty authError}">
            <br>
            <p style="color: #e75b09; font-weight: bold">Before viewing these pages, sign in</p>
        </c:if>
        <c:if test = "${not empty roleError}">
            <br>
            <p style="color: #e75b09; font-weight: bold">Access is denied. You aren't guest of our hotel.</p>
        </c:if>
        <c:if test = "${not empty error}">
            <br>
            <p style="color: #e75b09; font-weight: bold">${error}</p>
        </c:if>
        <form action="<c:url value="signin"/>" class="validateOnEmptyField" method="POST">

            <div class="form-outline mb-4">
                <input name="email" value="${form.email}" type="email" placeholder="Email" class="form-control field" />
            </div>

            <div class="form-outline mb-4">
                <input name="password" type="password" placeholder="Password" id="password" class="form-control field" />
            </div>

            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="rememberMe" value="true" id="flexCheckDisabled">
                <label class="form-check-label" for="flexCheckDisabled">
                    Remember me
                </label>
            </div>
            <br>

            <div class="text-end">
                <form action="<c:url value="signin"/>" method="POST">
                    <button type="submit" class="btn" style="background: #D2B48C">Sign In</button>
                </form>
                <div><br></div>
                <form action="<c:url value="welcome"/>" method="GET">
                    <button type="submit" class="btn" style="background: #FFEBCD">Home Page</button>
                </form>
            </div>
        </form>

        <script src="<c:url value="/js/validateEmptyField.js"/>"></script>
    </div>
</t:cardSimpleLayout>

