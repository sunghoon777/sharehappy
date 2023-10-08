<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <title><spring:message code="title.myPage"/></title>
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
    <!-- font awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <!-- custom -->
    <link rel="stylesheet" href="/css/my/MyPageOrganization.css">
    <link rel="stylesheet" href="/css/common/Font.css">
    <script src="/js/my/MyPageOrganization.js"></script>
</head>
<body>
<!--sidebar-->
<nav id="sidebarMenu" class="collapse d-lg-block sidebar collapse bg-white">
    <div class="position-sticky">
        <div class="main-logo mt-4">
            <a class="main-logo-text navbar-brand" href="/main"><img class="home" src="https://img.icons8.com/ios-filled/50/12B886/charity-box.png" width="50" height="50" alt="home"><spring:message code="homepageName"/></a>
        </div>
        <div class="list-group list-group-flush mx-3 mt-4">
            <span class="list-group-item list-group-item-action py-2 ripple" aria-current="true">
                <i class="fas fa-tachometer-alt fa-fw me-3" style="color: #10c838;"></i><span class="sidebar-element"><spring:message code="posts.myPage"/></span>
            </span>
        </div>
    </div>
</nav>
<!--sidebar-->
<!--Main layout-->
<main style="margin-top: 58px">
    <div id="main" class="container pt-4">
       <div class="row">
           <div class="col-3 mb-4 px-1 py-1">
               <div class="card">
                   <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B6%801.jpg" class="card-img-top">
                   <div class="card-body container">
                       <p class="title mt-1">기부해주세요</p>
                       <p class="organization-name mt-1">기부단체1</p>
                       <div class="progress" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
                           <div class="progress-bar" style="width: 20%"></div>
                       </div>
                       <div class="d-flex justify-content-between align-items-center">
                           <span class="percentage">20%</span>
                           <span>20000<spring:message code="moneyUnit"/></span>
                       </div>
                   </div>
               </div>
               <div class="d-flex justify-content-center align-items-center mt-3">
                   <button type="button" class="btn btn-danger">삭제</button>
               </div>
           </div>
       </div>
    </div>
</main>
<!--Main layout-->
</body>
</html>


