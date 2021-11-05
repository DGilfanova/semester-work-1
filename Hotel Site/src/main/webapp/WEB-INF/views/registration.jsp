<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 03.11.2021
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardSimpleLayout title="Registration">
    <img class="card-img-top" src="images/booking.jpg" style="height: 250px" alt="Registration">
    <div class="card-body p-4 p-md-5">
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">Booking</h3>

        <c:if test = "${not empty bookingUserError}">
            <p style="color: #e75b09; font-weight: bold">First enter the information about yourself.</p>
        </c:if>
        <c:if test = "${not empty error}">
            <p style="color: #e75b09; font-weight: bold;">${error}</p>
        </c:if>
        <c:if test = "${not empty editProfile}">
            <div class="container text-left">
                <form action="<c:url value="edituser"/>">
                    <button type="submit" class="btn" style="background: #D2B48C">Edit</button>
                </form>
                <br><br>
            </div>
        </c:if>

        <form action="<c:url value="registration"/>" class="registration" method="POST">

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

            <div class="form-outline mb-4">
                <input name="email" type="email" value="${form.email}" placeholder="Email" class="form-control field email" />
            </div>
            <br>
            <h6 style="color: #483D8B">* If you have already booked a room in our hotel, then enter your password.</h6>
            <h6 style="color: #483D8B">* If this is your first time with us, then come up with and remember the password.</h6>
            <div class="form-outline mb-4">
                <input name="password" type="password" placeholder="Password" id="password" class="form-control field password" />
            </div>

            <form action="<c:url value="registration"/>" method="POST">
                <button type='submit' class="btn" style="background: #D2B48C">Next</button>
            </form>
        </form>
        <div><br></div>
        <form action="<c:url value="welcome"/>" method="GET">
            <button type="submit" class="btn" style="background: #FFEBCD">Home Page</button>
        </form>

        <script src="<c:url value="/js/validateRegistration.js"/>"></script>
    </div>
</t:cardSimpleLayout>
