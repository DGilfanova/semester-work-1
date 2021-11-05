<%@tag description="Basic layout" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
        <a class="navbar-brand"><img src="images/logo_lux.jpg" width="100" height="70"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#menuButton" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menuButton">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="welcome"/>">Welcome</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="registration"/>">Booking</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="restaurant"/>">Restaurants</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="room"/>">Rooms/Suites</a>
                </li>
                <c:if test = "${empty auth}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="suggestion"/>">Suggestions</a>
                    </li>
                </c:if>

            </ul>
            <div class="text-end">
                <form action="<c:url value="welcome"/>" method="POST">
                    <button type="submit" name="retrieveBookingButton" value="true" class="btn btn-outline-light me-2">Retrieve booking</button>
                    <c:if test = "${empty auth}">
                        <button type="submit" name="signinButton" value="true" class="btn" style="background: #D2B48C">Sign in</button>
                    </c:if>
                    <c:if test = "${not empty auth}">
                        <button type="submit" name="profileButton" value="true" class="btn" style="background: #D2B48C">Profile</button>
                    </c:if>
                </form>
            </div>
        </div>
    </nav>
    <!-- Navbar -->
</header>
