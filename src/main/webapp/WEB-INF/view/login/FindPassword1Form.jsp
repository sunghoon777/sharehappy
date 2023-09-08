<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="/js/login/FindPassword1.js"></script>
    <title><spring:message code="title.findpassword"/></title>
    <style>
        #error{
            color: red;
        }
    </style>
</head>
<body>
<div class="container d-flex flex-column">
    <div class="row align-items-center justify-content-center
          min-vh-100">
        <div class="col-12 col-md-8 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="mb-4">
                        <h5><spring:message code="subtitle1.findpassword"/></h5>
                        <p class="mb-2"><spring:message code="subtitle2.findpassword"/></p>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label"><spring:message code="emailButtonLabel.findpassword"/></label>
                        <input type="email" id="email" class="form-control" name="email" placeholder="이메일 입력">
                    </div>
                    <div class="mb-3 d-grid">
                        <button id="findpasword-btn" type="submit" class="btn btn-success"><spring:message code="findpasswordButton.findpassword"/></button>
                    </div>
                    <p id="error"></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
