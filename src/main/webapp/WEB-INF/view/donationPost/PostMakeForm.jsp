<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><spring:message code="title.postMake"/></title>
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
          integrity="sha256-7ZWbZUAi97rkirk4DcEp4GWDPkWpRMcNaEyXGsNXjLg=" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    <!-- summernote -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css"
          integrity="sha256-IKhQVXDfwbVELwiR0ke6dX+pJt0RSmWky3WB2pNx9Hg=" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"
            integrity="sha256-u7e5khyithlIdTpu22PHhENmPcRdFiHRjhAuHcs05RI=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.js"
            integrity="sha256-5slxYrL5Ct3mhMAp/dgnb5JSnTYMtkr4dHby34N10qw=" crossorigin="anonymous"></script>
    <!-- summer note language pack -->
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/lang/summernote-ko-KR.min.js"
            integrity="sha256-y2bkXLA0VKwUx5hwbBKnaboRThcu7YOFyuYarJbCnoQ=" crossorigin="anonymous"></script>
    <!--sweet alert-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script  >
    <!-- custom -->
    <link rel="stylesheet" href="/css/common/Font.css">
    <link rel="stylesheet" href="/css/donationPost/PostMakeFom.css">
    <script src="/js/donationPost/PostMakeForm.js"></script>
</head>
<body>
<div class="container" role="main">
    <h2 class="mb-3"><spring:message code="subtitle.postMake"/></h2>
    <div class="mb-3">
        <label for="title"><spring:message code="titleLabel.postMake"/></label>
        <input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해 주세요">
        <p id="title-error" class="error"></p>
    </div>
    <div class="mb-3">
        <label for="summernote"><spring:message code="contentLabel.postMake"/></label>
        <textarea id="summernote" name="editordata"></textarea>
        <p id="content-error" class="error"></p>
    </div>
    <div class="mt-1 mb-3">
        <label for="category " class="form-label"><spring:message code="categoryLabel.postMake"/></label>
        <select id="category " class="form-select" name="category">
            <c:forEach var="category" items="${categoryList}" varStatus="s">
                <option value="${category.name}">${category.krName}</option>
            </c:forEach>
        </select>
        <p id="category-error" class="error"></p>
    </div>
    <div class="mb-3">
        <label for="targetAmount" class="form-label"><spring:message code="targetAmountLabel.postMake"/></label>
        <input type="text" class="form-control" id="targetAmount">
        <p id="targetAmount-error" class="error"></p>
    </div>
    <div class="mb-3">
        <label for="endDate" class="form-label"><spring:message code="endDateLabel.postMake"/></label>
        <br>
        <input id="endDate" type="date" class="date-input">
        <p id="endDate-error" class="error"></p>
    </div>
    <div class="mb-3">
        <label for="thumbnail" class="form-label"><spring:message code="thumbNailLabel.postMake"/></label>
        <input class="form-control" type="file" name="image" id="thumbnail">
        <p id="thumbnail-error" class="error"></p>
    </div>
    <div class="mb-5">
        <div class="d-grid gap-2">
            <button id="submit-button" type="button" class="btn btn-outline-success"><spring:message code="submitButton.postMake"/></button>
        </div>
    </div>
</div>
</body>
</html>