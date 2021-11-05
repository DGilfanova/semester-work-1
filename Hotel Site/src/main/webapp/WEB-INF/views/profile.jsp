<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 30.10.2021
  Time: 8:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Profile">
    <body>
        <div class="container-fluid" style="max-width: 60%">
            <div class="row centered">
                <div class="container text-right">
                    <br><br><br><br><br><br>
                    <c:if test = "${not empty user}">
                        <h1 style="text-align: center">Welcome ${user.lastName} ${user.firstName}!</h1>
                    </c:if>
                    <br>
                    <form action="<c:url value="showorders"/>">
                        <button type="submit" class="btn" style="background: #D2B48C">Your orders</button>
                    </form>
                    <form action="<c:url value="orderhistory"/>">
                        <button type="submit" class="btn" style="background: #D2B48C">History</button>
                    </form>
                    <form action="<c:url value="logout"/>">
                        <button type="submit" class="btn" style="background: #D2B48C">Logout</button>
                    </form>
                </div>
                <div class="container">
                    <br>
                    <h3 style="text-align: center">You can order all these suggestion</h3><hr>
                    <br><br>
                </div>

                <%@include file="/WEB-INF/views/_suggestionsTable.jsp" %>
            </div>
        </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>
</t:mainLayout>