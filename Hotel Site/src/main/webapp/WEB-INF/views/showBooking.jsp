<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 01.11.2021
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardSimpleLayout title="Show booking">
    <img class="card-img-top" src="images/hotelView1.jpg" style="height: 250px" alt="Hotel photo">
    <div class="card-body p-4 p-md-5">
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-3 px-md-2">Your booking</h3>
        <br>

        <div class="form-group">
            <label for="1" style="color: #483D8B; font-weight: bold">Email address</label>
            <div class="form-control" id="1">${booking.user.email}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="2" style="color: #483D8B; font-weight: bold">First name</label>
            <div class="form-control" id="2">${booking.user.firstName}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="3" style="color: #483D8B; font-weight: bold">Last name</label>
            <div class="form-control" id="3">${booking.user.lastName}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="4" style="color: #483D8B; font-weight: bold">Patronymic</label>
            <div class="form-control" id="4">${booking.user.patronymic}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="5" style="color: #483D8B; font-weight: bold">Passport number</label>
            <div class="form-control" id="5">${booking.user.passportNumber}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="6" style="color: #483D8B; font-weight: bold">Room number</label>
            <div class="form-control" id="6">${booking.room.number}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="7" style="color: #483D8B; font-weight: bold">Arrival date</label>
            <div class="form-control" id="7">${booking.arrivalDate}</div>
            <br>
        </div>

        <div class="form-group">
            <label for="8" style="color: #483D8B; font-weight: bold">Departure date</label>
            <div class="form-control" id="8">${booking.departureDate}</div>
            <br>
        </div>

        <br>
        <h5>Booking editing is temporarily unavailable.</h5>

        <div class="text-end">
            <br><br>
            <form action="<c:url value="welcome"/>">
                <button type="submit" class="btn" style="background: #D2B48C">Home page</button>
            </form>
            <div><br></div>
        </div>
    </div>
</t:cardSimpleLayout>
