<%--
  Created by IntelliJ IDEA.
  User: r077r
  Date: 30.10.2021
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:cardLayout title="Edit Order">
    <img class="card-img-top" src="images/order.jpg" style="height: 270px" alt="Hotel photo">
    <div class="card-body p-4 p-md-5">
        <h3 class="mb-4 pb-2 pb-md-0 mb-md-3 px-md-2">Edit order</h3>
        <br><br>
        <c:if test = "${not empty error}">
            <br>
            <p style="color: #e75b09; font-weight: bold">${error}</p>
        </c:if>
        <form action="<c:url value="editorder"/>" class="validateOnEmptyField" method="POST">

            <label class="form-label select-label">Suggestion:</label>
            <input type="hidden" name="orderId" value="${order.id}">
            <input type="hidden" name="suggestionId" value="${order.suggestion.id}">
            <input value="${order.suggestion.name}" type='text' disabled="disabled"/>
            <br><br>
            <div class="row">
                <div class='col-sm-6'>
                    <div class="form-group">
                        <label class="form-label select-label">Choose date</label>
                        <div class='input-group date' id='datetimepicker1'>
                            <input name="date" value="${form.date}" type='text' class="form-control field" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <script type="text/javascript">
                    $(function () {
                        $('#datetimepicker1').datetimepicker({
                            format: 'YYYY-MM-DD HH:mm'
                        });
                    });
                </script>
            </div>

            <div class="text-end">
                <form action="<c:url value="editorder"/>" method="POST">
                    <button type="submit" class="btn" style="background: #D2B48C">Edit</button>
                </form>
                <div><br></div>
                <form action="<c:url value="showorders"/>" method="GET">
                    <button type="submit" class="btn" style="background: rgba(0, 0, 0, 0.05)">Back</button>
                </form>
            </div>
        </form>

        <script src="<c:url value="/js/validateEmptyField.js"/>"></script>
    </div>
</t:cardLayout>