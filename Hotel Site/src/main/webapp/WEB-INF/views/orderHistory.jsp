<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 05.11.2021
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Orders History">
    <body>
    <div class="container-fluid" style="max-width: 60%">
        <div class="row centered">
            <div class="container text-right">
                <br><br><br>
                <form action="<c:url value="profile"/>">
                    <button type="submit" class="btn btn-lg" style="background: #D2B48C">Back</button>
                </form>
            </div>
            <div class="container">
                <h1 style="text-align: center">Orders History</h1><hr>
                <br>
            </div>

            <%@include file="/WEB-INF/views/_historyTable.jsp" %>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>
</t:mainLayout>


