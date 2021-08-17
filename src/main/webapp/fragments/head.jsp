<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="${contextPath}/resources/static/assets/favicon.ico" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="${contextPath}/resources/static/css/styles.css" rel="stylesheet" />

    <style>
        body {
            background-color: #3e3e3e;
            color: white;
        }
        .btn.btn-primary {
            color: #fff;
            background-color: #337ab7;
            border-color: #2e6da4;
            border-radius: 30px;
        }
    </style>
</head>