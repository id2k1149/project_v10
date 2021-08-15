<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<jsp:include page="fragments/head.jsp"/>
<head>
    <title>Welcome</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<!-- Page content-->
<div class="container">
    <div class="row mb-4" data-aos="fade-up" data-aos-delay="200">
        <div class="col-lg-6 mr-auto">
            <h1>Survey for your company</h1>
            <p class="mb-5">Lorem ipsum dolor sit amet consectetur adipisicing elit,
                sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum
                dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident,
                sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            <div>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <a href="${contextPath}/vote" class="btn btn-primary mr-2 mb-2">
                        Vote
                    </a>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <a href="${contextPath}/login" class="btn btn-primary mr-2 mb-2">
                        Sign in
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>