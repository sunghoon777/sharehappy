<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <title>로그인</title>
    <style>
        body {
            background: #007bff;
            background: linear-gradient(to right, #0062E6, #33AEFF);
        }
        .btn-login {
            font-size: 0.9rem;
            letter-spacing: 0.05rem;
            padding: 0.75rem 1rem;
        }
        .login{
            border-color: #33AEFF !important;
        }
        .login:hover .login:focus{
            background-color: #33AEFF !important;
            border-color: #33AEFF !important;
            color: white !important;
        }
        .error>p{
            color: red;
        }
        .findPassword{
            text-decoration: none;
            color: #33AEFF;
            line-height: 100%;
        }
        .form-check{
            display: flex;
            justify-content: space-between;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card border-0 shadow rounded-3 my-5">
                <div class="card-body p-4 p-sm-5">
                    <div class="text-center mb-5 fw-light fs-5">
                        <a href="/main"><img src="https://img.icons8.com/ios-filled/50/12B886/charity-box.png"/> alt="home></a>
                    </div>
                    <form action="/login" method="post">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email" value="${cookie.email.value}">
                            <label for="floatingInput">이메일</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
                            <label for="floatingPassword">패스워드</label>
                        </div>
                        <div class="form-check mb-3">
                            <span>
                                <input class="form-check-input" type="checkbox" value="true" id="rememberPasswordCheck" name="remember" checked>
                                <label class="form-check-label" for="rememberPasswordCheck">아이디 기억하기</label>
                            </span>
                            <a class="findPassword" href="/findPassword1">비밀번호 찾기</a>
                        </div>
                        <div class="d-grid">
                            <button class="btn btn-outline-primary btn-login text-uppercase fw-bold login" type="submit">로그인</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
