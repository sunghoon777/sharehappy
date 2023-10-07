<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title><spring:message code="title.donation"/></title>
    <!-- bootstrap  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <!--sweet alert-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="/js/donation/Donation.js"></script>
    <!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <!-- iamport.payment.js -->
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <!-- fontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <!-- font  -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!-- custom -->
    <link rel="stylesheet" href="/css/common/Font.css">
    <link rel="stylesheet" href="/css/donation/Donation.css">

</head>
<body class="main_body">
<jsp:include page="/WEB-INF/view/menu/Menu.jsp"/>
<div class="container">
    <div class="row mt-2">
        <div class="col"></div>
        <div class="col-8 donation-container px-2 py-5 mb-5">
            <div class="title-container mb-5">
                <h1 class="title"><spring:message code="title.donation"/></h1>
            </div>
            <div class="row justify-content-md-center">
                <div class="col-md-auto">
                    <img src="${donationPostSummary.thumbNailImageUrl}" class="thumbnail" alt="thumbnail">
                </div>
                <div class="col-md-auto d-flex flex-column justify-content-center">
                    <p class="mb-5 post-title">${donationPostSummary.title}</p>
                    <p class="organization-name"><spring:message code="organizationName.donation" arguments="${donationPostSummary.organizationName}"/></p>
                </div>
                <div class="mt-5">
                    <h1 class="text-donation"><spring:message code="text.donation"/></h1>
                    <p class="text2-donation"><spring:message code="text2.donation"/></p>
                </div>
                <div class="d-flex align-items-center justify-content-center">
                    <span class="me-5 fw-bold"><spring:message code="donation-amount.donation"/></span><input type="text" class="form-control" id="donation-amount" value="10,000"><span style="color: #00ab33;"><spring:message code="moneyUnit"/></span>
                </div>
                <div class="d-flex justify-content-center my-5">
                    <button id="donate-button" type="button" class="btn btn-success btn-lg mx-3" postId="${donationPostSummary.postId}"><spring:message code="donationButton.donation"/></button>
                    <button onclick="window.location='/donationPost/${donationPostSummary.postId}'" id="return-button" type="button" class="btn btn-light btn-lg mx-3"><spring:message code="returnButton.donation"/></button>
                </div>
                <div class="my-5 d-flex justify-content-center">
                    <p id="error"></p>
                </div>
            </div>
        </div>
        <div class="col"></div>
    </div>
</div>
</body>
</html>
