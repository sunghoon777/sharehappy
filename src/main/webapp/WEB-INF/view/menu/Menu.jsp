<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="kr">
<head>
	<title>메뉴</title>
	<!-- bootstrap  -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<!-- fontAwesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
	<!-- font  -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
	<!-- custom -->
	<link rel="stylesheet" href="/css/menu/Menu.css">
</head>
<body>
<div class="container">
	<nav class="navbar">
		<div class="container-fluid" style="background-color: white;">
			<a class="navbar-brand d-flex align-items-center justify-content-center" href="/main">
				<img src="https://img.icons8.com/ios-filled/50/12B886/charity-box.png" alt="ShareHappy홈" width="50" height="50" class="d-inline-block align-text-top">
				<spring:message code="home.menu"/>
			</a>
			<ul class="nav justify-content-end">
				<div class="d-flex align-items-center justify-content-center">
					<c:choose>
						<c:when test="${empty isLogin}">
							<li class="nav-item">
								<a class="nav-link" href="/login/form">
									<span><spring:message code="login.menu"/></span>
								</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/signup/form">
									<span><spring:message code="signup.menu"/></span>
								</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="#">
									<span><spring:message code="myInfo.menu"/></span>
								</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="#">
									<span><spring:message code="logout.menu"/></span>
								</a>
							</li>
						</c:otherwise>
					</c:choose>
					<li class="nav-item">
						<a class="nav-link" href="#">
							<i class="fa-solid fa-magnifying-glass fa-lg"></i>
						</a>
					</li>
				</div>
			</ul>
		</div>
	</nav>
</div>
</body>
</html>
