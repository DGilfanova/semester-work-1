<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 19.10.2021
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Welcome">
    <body>
        <!-- Background image -->
        <div class="p-5 text-center bg-image" style="background-image: url('https://hotelss.net/images/vilu-beach15.jpg'); min-height: 730px; width: 100%;">
            <div class="mask">
                <div class="d-flex justify-content-center align-items-center h-100">
                    <div class="text-white">
                        <br><br>
                        <h4 class="mb-3">The best rest of your life</h4>
                        <form action="<c:url value="registration"/>">
                            <button type="submit" class="btn btn-outline-light btn-lg">Booking</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid" style="max-width: 80%">
            <div class="row centered">
                <br><br>
                <p><a id="ref_offer"></a></p>
                <div class="container 2">
                    <br><br><br><br>
                    <h1>For guest of our hotel!</h1>
                    <br><br>
                </div>
                <div class="col-lg">
                    <img src="images/offers.jpg" width="500" height="300">
                </div>
                <div class="col-lg">
                    <h3 class="widget-title">Offers for our hotel guests</h3>
                    <ul class="widget-list">
                        <li><h5>Free breakfast and dinner</h5></li>
                        <li><h5>Massage, outdoor beach and cosmetologist services</h5></li>
                        <li><h5>Flexible service ordering system</h5></li>
                        <li><h5>Staff who speak three languages</h5></li>
                    </ul>
                </div>
            </div>
            <br><br><br>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>
</t:mainLayout>

