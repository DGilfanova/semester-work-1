<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 03.11.2021
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardSimpleLayout title="Edit Order">
    <img class="card-img-top" src="images/editUser.jpg" style="height: 270px" alt="Hotel photo">
    <div class="card-body p-4 p-md-5">
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-3 px-md-2">Edit user</h3>
        <br><br>
        <c:if test = "${not empty error}">
            <br>
            <p style="color: #e75b09; font-weight: bold">${error}</p>
        </c:if>
        <form action="<c:url value="edituser"/>" class="registration" method="POST">

            <h6 style="color: #483D8B">* Enter your email and password.</h6>
            <div class="form-outline mb-4">
                <input type='email' name="email" value="${form.email}" placeholder="Email" class="form-control email field" />
            </div>

            <div class="form-outline mb-4">
                <input name="password" type="password" placeholder="Password" id="password" class="form-control field password" />
            </div>

            <br>
            <h6 style="color: #483D8B">* Enter new personal information.</h6>
            <div class="form-outline mb-4">
                <input type="text" name="firstName" value="${form.firstName}" placeholder="First Name" class="form-control name field" />
            </div>

            <div class="form-outline mb-4">
                <input type="text" name="lastName" value="${form.lastName}" placeholder="Last Name" class="form-control name field" />
            </div>

            <div class="form-outline mb-4">
                <input type="text" name="patronymic" value="${form.patronymic}" placeholder="Patronymic" class="form-control name field" />
            </div>

            <div class="form-outline mb-4">
                <input type="text" name="passportNumber" value="${form.passportNumber}" placeholder="Passport Number" class="form-control field passport" />
            </div>

            <div class="form-outline mb-4">
                <input type="text" name="mobileNumber" value="${form.mobileNumber}" placeholder="Mobile Number" class="form-control field mobile" />
            </div>

            <div class="text-end">
                <form action="<c:url value="edituser"/>" method="POST">
                    <button type="submit" class="btn" style="background: #D2B48C">Edit</button>
                </form>
            </div>
        </form>
        <div><br></div>
        <form action="<c:url value="registration"/>" method="GET">
            <button type="submit" class="btn" style="background: #FFEBCD">Back</button>
        </form>

        <script src="<c:url value="/js/validateRegistration.js"/>"></script>
    </div>
</t:cardSimpleLayout>
