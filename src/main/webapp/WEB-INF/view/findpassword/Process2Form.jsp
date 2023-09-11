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
    <link rel="stylesheet" href="/css/findpassword/Process2Form.css">
    <script src="/js/findpassword/Process2Form.js"></script>
    <title><spring:message code="title.findpassword"/></title>
</head>
<body>
<div class="container height-100 d-flex justify-content-center align-items-center">
    <div class="position-relative">
        <div class="card p-2 text-center">
            <h6><spring:message code="randomCodeSubtitle.findpassword"/></h6>
            <div id="randomCode" class="inputs d-flex flex-row justify-content-center mt-2">
                <input class="m-2 text-center form-control rounded randomcode" type="text" id="first" name="1" maxlength="1" required/>
                <input class="m-2 text-center form-control rounded randomcode" type="text" id="second" name="2" maxlength="1" required/>
                <input class="m-2 text-center form-control rounded randomcode" type="text" id="third" name="3" maxlength="1" required/>
                <input class="m-2 text-center form-control rounded randomcode" type="text" id="fourth" name="4" maxlength="1" required/>
                <input class="m-2 text-center form-control rounded randomcode" type="text" id="fifth" name="5" maxlength="1" required/>
            </div>
            <div class="mt-4">
                <button id="submit-btn" type="submit" class="btn btn-outline-success px-4"><spring:message code="submit.process2.findpassword"/></button>
            </div>
            <p id="error" class="text-center error"></p>
        </div>
    </div>
</div>
</body>
</html>
