<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- Responsive navbar-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Company polls</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span
                class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" aria-current="page"
                                        href="${contextPath}/welcome">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="${contextPath}/vote">Vote</a></li>
                <li class="nav-item"><a class="nav-link" href="${contextPath}/result">Results</a></li>
                <li class="nav-item">
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <form id="logoutForm" method="POST" action="${contextPath}/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <a class="nav-link"
                               onclick="document.forms['logoutForm'].submit()">${pageContext.request.userPrincipal.name} -> Logout</a>
                        </form>

                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</nav>
<br>
<br>

