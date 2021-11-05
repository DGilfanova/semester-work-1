<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 01.11.2021
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardSimpleLayout title="Retrieve booking">
    <img class="card-img-top" src="images/retrieveBooking.jpg" style="height: 250px" alt="Hotel photo">
    <div class="card-body p-4 p-md-5">
        <c:if test = "${not empty chooseBookingError}">
            <p style="color: #e75b09; font-weight: bold">First enter booking info to see your booking.</p>
        </c:if>
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-3 px-md-2">Retrieve your booking</h3>
        <br>
        <form action="<c:url value="retrievebooking"/>" class="validateOnEmptyField" method="POST">

            <c:if test = "${not empty booking}">
                <br>
                <p style="color: #008B8B; font-weight: bold">Your room has been successfully booked. You can check the information.</p>
            </c:if>
            <c:if test = "${not empty error}">
                <br>
                <p style="color: #e75b09; font-weight: bold">${error}</p>
            </c:if>

            <h6 style="color: #483D8B">* We send this number on email.</h6>
            <div class="form-outline mb-4">
                <input name="reservationNumber" value="${form.reservationNumber}" type="text" placeholder="Reservation Number" class="form-control field" />
            </div>

            <div class="form-outline mb-4">
                <input name="email" type="email" value="${form.email}" placeholder="Email" class="form-control field" />
            </div>

            <div class="form-outline mb-4">
                <input name="lastName" type="text" value="${form.lastName}" placeholder="Last Name" class="form-control field" />
            </div>

            <div class="text-end">
                <form action="<c:url value="retrievebooking"/>" method="POST">
                    <button type="submit" class="btn" style="background: #D2B48C">Retrieve</button>
                </form>
                <div><br></div>
                <form action="<c:url value="welcome"/>" method="GET">
                    <button type="submit" class="btn" style="background: rgba(0, 0, 0, 0.05)">Home Page</button>
                </form>
            </div>
        </form>

        <script src="<c:url value="/js/validateEmptyField.js"/>"></script>
    </div>
</t:cardSimpleLayout>
