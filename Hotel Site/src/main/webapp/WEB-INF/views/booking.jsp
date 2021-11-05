<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 21.10.2021
  Time: 8:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardLayout title="Booking">
    <img class="card-img-top" src="images/booking.jpg" style="height: 250px" alt="Booking">
    <div class="card-body p-4 p-md-5">
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">Booking</h3>
        <br>

        <c:if test = "${not empty error}">
            <br>
            <p style="color: #e75b09; font-weight: bold">${error}</p>
        </c:if>

        <form action="<c:url value="booking"/>" class="validateOnEmptyField" method="POST">

            <label class="form-label select-label">Choose room</label>
            <select name="roomNumber" class="select form-control mb-4 room field">
                <option disabled>Choose room</option>
                <c:forEach items="${rooms}" var="room">
                    <option value=${room.number}>${room.name}</option>
                </c:forEach>
            </select>

            <div class="row">
                <div class='col-sm-6'>
                    <div class="form-group">
                        <label class="form-label select-label">Choose arrival date</label>
                        <div class='input-group date' id='datetimepicker1'>
                            <input name="arrivalDate" type='text' class="form-control field" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <script type="text/javascript">
                    $(function () {
                        $('#datetimepicker1').datetimepicker({
                            format: 'YYYY-MM-DD'
                        });
                    });
                </script>
            </div>

            <div class="row">
                <div class='col-sm-6'>
                    <div class="form-group">
                        <label class="form-label select-label">Choose departure date</label>
                        <div class='input-group date' id='datetimepicker2'>
                            <input name="departureDate" type='text' class="form-control field" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <script type="text/javascript">
                    $(function () {
                        $('#datetimepicker2').datetimepicker({
                            format: 'YYYY-MM-DD'
                        });
                    });
                </script>
            </div>

            <br>
            <h4 style="color: #483D8B; font-weight: bold">The reservation number will be sent to your email.</h4>
            <h4 style="color: #483D8B;font-weight: bold">Use it to view your booking.</h4>
            <br>
                <form action="<c:url value="booking"/>" method="POST">
                    <button type='submit' class="btn" style="background: #D2B48C">Send</button>
                </form>
        </form>

        <script src="<c:url value="/js/validateEmptyField.js"/>"></script>
    </div>
</t:cardLayout>