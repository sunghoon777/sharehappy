<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="kr">
<head>
    <!-- bootstrap  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <!-- fontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    <!-- font  -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!--sweet alert-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!-- custom -->
    <link rel="stylesheet" href="/css/main/Main.css">
    <link rel="stylesheet" href="/css/common/Font.css">
    <script src="/js/main/Main.js"/>
    <title><spring:message code="title.main"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/menu/Menu.jsp"/>
<div class="container category mt-5">
    <ul class="d-flex align-items-center justify-content-between">
        <c:forEach var="category" items="${categoryList}" varStatus="s">
            <li>
                <a id="${category.name}" class="category-select">
                    <img src="${category.iconUrl}" alt="${category.krName}" width="28" height="28" class="d-inline-block align-text-top">
                    <span>${category.krName}</span>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<div class="container mt-5 d-flex justify-content-between">
    <div class="col-3">
        <p class="h4"><spring:message code="postCount.main" arguments="${postCount}"/></p>
    </div>
    <div class="col-3">
        <select id="sort-select" class="form-select">
            <c:forEach var="postSortCriteria" items="${postSortCriteriaList}" varStatus="s">
                <c:choose>
                    <c:when test="${postSortCriteria eq 'DESCENDING_FUND_PERCENTAGE'}">
                        <option selected value="${postSortCriteria}">${postSortCriteria.getValue()}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${postSortCriteria}">${postSortCriteria.getValue()}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </div>
</div>
<div class="container main mt-5">
    <div id="postSummary-container" class="row">
        <c:forEach var="post" items="${postSummaryList}" varStatus="">
            <div class="col-3 mb-4 px-1 py-1"  onclick="window.location.href='/donationPost/${post.postId}'">
                <div class="card">
                    <img src="${post.thumbNailImageUrl}" class="card-img-top">
                    <div class="card-body container">
                        <p class="title mt-1">${post.title}</p>
                        <p class="organization-name mt-1">${post.organizationName}</p>
                        <div class="progress" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar" style="width: ${fn:substringBefore(post.fundPercentage, '.')}%"></div>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="percentage">${fn:substringBefore(post.fundPercentage, '.')}%</span>
                            <fmt:formatNumber var="formattedAmount" value="${post.currentAmount}" type="currency" currencyCode="KRW"/>
                            <span>${fn:substringBefore(formattedAmount.replace('₩', ''),'.')}<spring:message code="moneyUnit"/></span>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div class="container mt-5 mb-5">
    <div class="d-grid gap-2">
        <button id="btn-more" class="btn" type="button">더보기</button>
    </div>
</div>
</body>
</html>
