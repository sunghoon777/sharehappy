<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="kr">
<head>
    <title>ShareHappy</title>
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
    <link rel="stylesheet" href="/style/main/Main.css">
</head>
<body>
<jsp:include page="/WEB-INF/view/menu/Menu.jsp"/>
<div class="container category mt-5">
    <ul class="d-flex align-items-center justify-content-between">
        <c:forEach var="category" items="${categoryList}">
            <li>
                <a href="">
                    <img src="${category.iconUrl}" alt="${category.name}" width="28" height="28" class="d-inline-block align-text-top">
                    <span>${category.name}</span>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<div class="container main mt-5">
    <div class="row">

    </div>
</div>
<div class="container mt-5">
    <div class="d-grid gap-2">
        <button id="btn-more" class="btn" type="button">더보기</button>
    </div>
</div>
<script src="/js/main/Main.js"/>
</body>
</html>
