<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- Responsive navbar-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${contextPath}/welcome">Company polls</a>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span
                    class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link" href="${contextPath}/vote">Vote</a></li>
                    <li class="nav-item"><a class="nav-link" href="${contextPath}/result">Results</a></li>
                    <security:authorize access="hasAuthority('ROLE_ADMIN')">
                        <li class="nav-item">
                            <c:if test="${info3 == null}">
                                <a class="nav-link active" style="color:#337ab7" href="${contextPath}/update">
                                    <strong>CREATE a new poll</strong>
                                </a>
                            </c:if>
                            <c:if test="${info3 != null}">
                                <a class="nav-link active" style="color:darkred" href="#">
                                    <strong>CREATE a new poll</strong>
                                </a>
                            </c:if>
                        </li>
                    </security:authorize>
                    <li class="nav-item">
                        <form id="logoutForm" method="POST" action="${contextPath}/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <a class="nav-link"
                               onclick="document.forms['logoutForm'].submit()">${pageContext.request.userPrincipal.name}
                                -> Logout</a>
                        </form>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</nav>
<br>
<br>

