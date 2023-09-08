<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <title>패스워드 찾기</title>
    <style>
        body {
            background-color: #33AEFF
        }

        .height-100 {
            height: 100vh
        }

        .card {
            width: 400px;
            border: none;
            height: 300px;
            box-shadow: 0px 5px 20px 0px #d2dae3;
            z-index: 1;
            display: flex;
            justify-content: center;
            align-items: center
        }

        .card h6 {
            color: #33AEFF;
            font-size: 20px
        }

        .inputs input {
            width: 40px;
            height: 40px
        }

        input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button {
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            margin: 0
        }

        .card-2 {
            background-color: #fff;
            padding: 10px;
            width: 350px;
            height: 100px;
            bottom: -50px;
            left: 20px;
            position: absolute;
            border-radius: 5px
        }

        .card-2 .content {
            margin-top: 50px
        }

        .card-2 .content a {
            color: #33AEFF;
        }

        .form-control:focus {
            box-shadow: none;
            border: 2px solid #33AEFF
        }

        .validate {
            border-radius: 20px;
            height: 40px;
            background-color: #33AEFF;
            border: 1px solid #33AEFF;
            width: 140px
        }
    </style>
</head>
<body>
<div class="container height-100 d-flex justify-content-center align-items-center">
    <div class="position-relative">
        <form action="/findPassword3" method="post">
            <div class="card p-2 text-center">
                <h6>이메일을 통해 받으신 코드를 입력하세요</h6>
                <div id="randomCode" class="inputs d-flex flex-row justify-content-center mt-2">
                    <input class="m-2 text-center form-control rounded" type="text" id="first" name="1" maxlength="1" required/>
                    <input class="m-2 text-center form-control rounded" type="text" id="second" name="2" maxlength="1" required/>
                    <input class="m-2 text-center form-control rounded" type="text" id="third" name="3" maxlength="1" required/>
                    <input class="m-2 text-center form-control rounded" type="text" id="fourth" name="4" maxlength="1" required/>
                    <input class="m-2 text-center form-control rounded" type="text" id="fifth" name="5" maxlength="1" required/>
                </div>
                <div class="mt-4">
                    <button type="submit" class="btn btn-danger px-4 validate">제출</button>
                </div>
                <c:if test="${!empty errorMessage}">
                    <p class="text-center error">${errorMessage}</p>
                </c:if>
            </div>
        </form>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function (event) {
        function randomCodeInput() {
            const inputs = document.querySelectorAll('#randomCode > *[id]');
            for (let i = 0; i < inputs.length; i++) {
                inputs[i].addEventListener('keydown', function (event) {
                    if (event.key === "Backspace") {
                        inputs[i].value = '';
                        if (i !== 0) inputs[i - 1].focus();
                    } else {
                        if (i === inputs.length - 1 && inputs[i].value !== '') {
                            return true;
                        } else if (event.keyCode >= 33 && event.keyCode <= 126 ) {
                            inputs[i].value = event.key;
                            if (i !== inputs.length - 1) inputs[i + 1].focus();
                            event.preventDefault();
                        }
                    }
                });
            }
        }
        randomCodeInput();
    });
</script>
</body>
</html>
