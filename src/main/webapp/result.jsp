<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<jsp:include page="fragments/head.jsp"/>
<head>
    <title>Result</title>
</head>

<body>
<!-- Responsive navbar-->
<jsp:include page="fragments/header.jsp"/>
<!-- Page content-->
<div class="container">
    <div class="container text-center">
        <div>
            <h1> Where to have a lunch? </h1>
            <c:if test="${error != null}">
                <p><strong>${error}</strong></p>
                <a href="${contextPath}/vote" class="btn btn-primary mr-2 mb-2">
                    Please vote
                </a>
            </c:if>
            <c:if test="${error == null}">
                <table class="table table-bordered" bgcolor="#708090">
                    <thead>
                    <tr>
                        <th>Answers</th>
                        <th class="text-center">Votes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sortedList}" var="result">
                        <tr>
                            <td>${result.diner}</td>
                            <td class="text-center">${result.votes}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
