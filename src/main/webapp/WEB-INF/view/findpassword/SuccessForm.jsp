<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
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
    <link rel="stylesheet" href="/css/common/Font.css">
    <link rel="stylesheet" href="/css/findpassword/SuccessForm.css">
    <script src="/js/findpassword/SuccessForm.js"></script>
    <title><spring:message code="title.findpassword"/></title>
</head>
<body>
<div class="container d-flex flex-column">
    <div class="row align-items-center justify-content-center min-vh-100">
        <div class="col-12 col-md-8 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="mb-4">
                        <h1><spring:message code="subtitle.success.findpassword"/></h1>
                    </div>
                    <div class="mb-3">
                        <h1 class="newPassword" id="newPassword">${newPassword}</h1>
                    </div>
                    <div class="mb-3 d-grid">
                        <button type="button" class="btn btn-danger" id="copyButton"><spring:message code="copy.success.findpassword"/></button>
                    </div>
                    <div class="mb-3 d-grid">
                        <button type="button" class="btn btn-success" onclick="window.location.href='/login/form'"><spring:message code="loginButton.success.findpassword"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
