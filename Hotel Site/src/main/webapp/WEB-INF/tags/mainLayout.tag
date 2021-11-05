<%@tag description="Basic layout" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <t:head/>
    </head>
    <body>
    <div class="container">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            You have been successfully registered! You have received a reservation number by email!
        </div>
    </div>
        <t:header/>
        <jsp:doBody/>
        <t:footer/>
    </body>
</html>