<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <!-- bootstrap  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <!-- font  -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <!--sweet alert-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!-- custom -->
    <link rel="stylesheet" href="/css/login/Login.css">
    <link rel="stylesheet" href="/css/common/Font.css">
    <script src="/js/login/Login.js"></script>
    <title><spring:message code="title.login"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card border-0 shadow rounded-3 my-5">
                <div class="card-body p-4 p-sm-5">
                    <div class="text-center mb-5 fw-light fs-5">
                        <a href="/main"><img src="https://img.icons8.com/ios-filled/50/12B886/charity-box.png"/></a>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="email" class="form-control" id="email" placeholder="name@example.com" name="email" value="${cookie.EMAIL_REMEMBER.value}">
                        <label for="email"><spring:message code="email.login"/></label>
                        <p id="error-email" class="error"></p>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="password" placeholder="Password" name="password">
                        <label for="password"><spring:message code="password.login"/></label>
                        <p id="error-password" class="error"></p>
                    </div>
                    <div class="form-check mb-3">
                            <span>
                                <input class="form-check-input" type="checkbox" value="true" id="remember_email" name="remember" checked>
                                <label class="form-check-label" for="remember_email"><spring:message code="rememberIdCheck.login"/></label>
                            </span>
                            <span>
                                <input class="form-check-input" type="checkbox" value="true" id="organization_login" name="remember">
                                <label class="form-check-label" for="remember_email">기관 단체 로그인</label>
                            </span>
                        <a class="find_password" href="/login/findPassword1/form"><spring:message code="findPassword.login"/></a>
                    </div>
                    <p id="error" class="error"></p>
                    <div class="d-grid">
                        <button id="login-button" class="btn btn-outline-success btn-login text-uppercase fw-bold login" type="submit"><spring:message code="loginButton.login"/></button>
                    </div>
                    <input id="redirectUrl" type="hidden" value="${redirectURL}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>