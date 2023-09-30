<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title><spring:message code="title.postView"/></title>
    <!-- bootstrap  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <!-- font  -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!-- custom -->
    <link rel="stylesheet" href="/css/common/Font.css">
    <link rel="stylesheet" href="/css/donationPost/PostView.css">
</head>
<body class="main_body">
<jsp:include page="/WEB-INF/view/menu/Menu.jsp"/>
<div class="container mt-5 mb-5">
    <div class="row">
        <div class="col"></div>
        <div class="col-6 article">
            <p class="mb-4 category">${donationPostDetail.categoryKrName}</p>
            <h1 class="mb-5">${donationPostDetail.title}</h1>
            <div class="mb-5 content">
                ${donationPostDetail.content}
            </div>
            <div class="d-grid gap-2">
                <div class="d-grid gap-2">
                    <button id="comment-button" type="button" class="btn btn-success"><spring:message code="commentButton"/></button>
                </div>
            </div>
            <jsp:include page="/WEB-INF/view/comment/Comments.jsp"/>
        </div>
        <!--sidebar -->
        <div class="col">
            <h1 class="percentage mb-3">${donationPostDetail.fundPercentage}%</h1>
            <div class="progress mb-3" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
                <div class="progress-bar" style="width: ${donationPostDetail.fundPercentage}%"></div>
            </div>
            <div class="mb-3 date"><fmt:formatDate value="${donationPostDetail.startdate}"
                                                   pattern="yyyy-MM-dd"/>~<fmt:formatDate
                    value="${donationPostDetail.enddate}" pattern="yyyy-MM-dd"/></div>
            <fmt:formatNumber var="currentAmount" value="${donationPostDetail.currentAmount}" type="currency"
                              currencyCode="KRW"/>
            <div class="mb-3 current_amount">${fn:substringBefore(currentAmount.replace('₩', ''),'.')}
                <spring:message code="moneyUnit"/> <spring:message code="currentAmount.comment.postView"/></div>
            <fmt:formatNumber var="targetAmount" value="${donationPostDetail.targetAmount}" type="currency"
                              currencyCode="KRW"/>
            <div class="mb-3 target_amount">${fn:substringBefore(targetAmount.replace('₩', ''),'.')}
                <spring:message code="moneyUnit"/> <spring:message code="targetAmount.comment.postView"/></div>
            <div class="d-grid gap-2">
                <div class="d-grid gap-2">
                    <button type="button" class="btn btn-success"><spring:message
                            code="donationButton.postView"/></button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
