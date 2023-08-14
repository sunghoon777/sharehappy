<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="kr">
<head>
    <title>테스트</title>
    <!-- bootstrap  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <!-- fontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    <!-- font  -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <style>
        *{
            font-family: 'Noto Sans KR', sans-serif;
        }
        .category ul{
            list-style: none;
        }
        .category a{
            border: 1px solid lightgrey;
            padding: 11px;
            border-radius: 30px;
            color: black;
            text-decoration: none;
        }
        .category a:hover{
            border-color: gray;
            color: black;
        }
        .card{
            cursor: pointer;
        }
        .card-img-top{
            height: 15rem;
            object-fit: cover;
        }
        .card img {
            filter: brightness(100%);
            transition: filter 0.3s ease;
        }
        .card:hover img{
            filter: brightness(80%);
        }
        .title{
            overflow: hidden;
            text-overflow: ellipsis;
            line-height: 1.0rem;
            max-height: 2.0rem;
            height: 2.0rem;
        }
        .organization-name{
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            color: grey;
        }
        .progress-bar{
            background-color: #7CD2B3 !important;
        }
        .percentage{
            color: #7CD2B3;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/view/menu/Menu.jsp"/>
<div class="container category mt-5">
    <ul class="d-flex align-items-center justify-content-between">
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EC%A0%84%EC%B2%B4.png" alt="전체" width="28" height="28" class="d-inline-block align-text-top">
                <span>전체</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EC%95%84%EB%8F%99%EC%B2%AD%EC%86%8C%EB%85%84.png" alt="아동•청소년" width="28" height="28" class="d-inline-block align-text-top">
                <span>아동•청소년</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EB%85%B8%EC%9D%B8.png" alt="어르신" width="28" height="28" class="d-inline-block align-text-top">
                <span>어르신</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EC%9E%A5%EC%95%A0%EC%9D%B8.png" alt="장애인" width="28" height="28" class="d-inline-block align-text-top">
                <span>장애인</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EB%8B%A4%EB%AC%B8%ED%99%94.png" alt="다문화" width="28" height="28" class="d-inline-block align-text-top">
                <span>다문화</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EC%A7%80%EA%B5%AC%EC%B4%8C.png" alt="지구촌" width="28" height="28" class="d-inline-block align-text-top">
                <span>지구촌</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EA%B0%80%EC%A1%B1%EC%97%AC%EC%84%B1.png" alt="가족•여성" width="28" height="28" class="d-inline-block align-text-top">
                <span>가족•여성</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EB%8F%99%EB%AC%BC.png" alt="동물" width="28" height="28" class="d-inline-block align-text-top">
                <span>동물</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%ED%99%98%EA%B2%BD.png" alt="환경" width="28" height="28" class="d-inline-block align-text-top">
                <span>환경</span>
            </a>
        </li>
        <li>
            <a href="">
                <img src="https://sharehappy.s3.ap-northeast-2.amazonaws.com/categoryIcon/%EA%B8%B0%ED%83%80.png" alt="기타" width="28" height="28" class="d-inline-block align-text-top">
                <span>기타</span>
            </a>
        </li>
    </ul>
</div>
<div class="container main mt-5">
    <div class="row">
        <div class="col-3 mb-4 px-1 py-1" onclick="">
            <div class="card">
                <img src="https://basalpost.com/wp-content/uploads/2021/12/211226-1.jpg" alt="게시글이미지" class="card-img-top">
                <div class="card-body container">
                    <p class="title mt-1">희귀병을 가진 바다의 치료를 도와주세요</p>
                    <p class="organization-name mt-1">밀양복지재단</p>
                    <div class="progress" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
                        <div class="progress-bar" style="width: 25%"></div>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="percentage">25%</span>
                        <span>28,700원</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
