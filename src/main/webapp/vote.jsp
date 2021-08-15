<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div>
            <h1> Where to have a lunch? </h1>
        </div>

        <c:if test="${error1 != null}">
            <p style="color:#0d6efd"><strong>${error1}</strong></p>
        </c:if>
        <c:if test="${error2 != null}">
            <p style="color:#0d6efd"><strong>${error2}</strong></p>
        </c:if>

        <form action="vote" method="post">

            <c:forEach items="${answersList}" var="answer">

                <input type="radio"
                       id="answer"
                       name="answer"
                       value="${answer.id}"
                />
                <label for="answer">${answer.title}</label>
                <table class="table table-bordered" bgcolor="#708090">
                    <thead>
                    <tr>
                        <th class="text-center">Menu</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${answer.infoTo.get(0).details}" var="entry">
                        <tr>
                            <td class="text-left">${entry.key} : ${entry.value}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
            </c:forEach>
            <br>
            <c:if test="${error2 == null}">
                <input type="submit" class="btn btn-primary" value="Vote"/>
            </c:if>
            <c:if test="${error2 != null}">
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
