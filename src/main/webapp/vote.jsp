<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<jsp:include page="fragments/head.jsp"/>
<head>
    <title>Vote</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<!-- Page content-->
<div class="container">
    <div class="container text-center">
        <c:if test="${info2 == null}">
            <div>
                <h1> Please make your choice! </h1>
            </div>
        </c:if>

        <c:if test="${info1 != null}">
            <%--            <h1>${role}</h1>--%>
            <p style="color:#337ab7"><strong>${pageContext.request.userPrincipal.name.toUpperCase()}, ${info1}</strong>
            </p>
        </c:if>
        <c:if test="${info2 != null}">
            <p style="color:#337ab7"><strong>${info2}</strong></p>
        </c:if>
        <security:authorize access="hasAuthority('ROLE_ADMIN')">
            <c:if test="${info3 != null}">
                <p style="color:#337ab7"><strong>${info3}</strong></p>
            </c:if>
        </security:authorize>
        <form action="vote" method="post">

            <c:forEach items="${dinersList}" var="diner">

                <input type="radio"
                       id="diner"
                       name="diner"
                       value="${diner.id}"
                />
                <label for="diner">${diner.title}</label>
                <table class="table table-bordered" bgcolor="#708090">
                    <thead>
                    <tr>
                        <th class="text-center">Menu</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${diner.menu.get(0).dishes}" var="entry">
                        <tr>
                            <td class="text-left">${entry.key} : ${entry.value}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
            </c:forEach>
            <br>
            <c:if test="${info2 == null}">
                <input type="submit" class="btn btn-primary" value="Vote"/>
            </c:if>
            <c:if test="${info2 != null}">
                <a href="${contextPath}/result" class="btn btn-primary mr-2 mb-2">
                    Result
                </a>
            </c:if>

        </form>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
