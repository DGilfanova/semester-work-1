<%@tag description="Simple card layout" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <t:head/>
</head>
<body>
<div style="background-color: rgba(0, 0, 0, 0.05);">
    <div class="row d-flex justify-content-center align-items-center">
        <div class="col-lg-8 col-xl-6">
            <div class="card rounded-3">
                <jsp:doBody/>
            </div>
        </div>
    </div>
</div>
</body>
</html>