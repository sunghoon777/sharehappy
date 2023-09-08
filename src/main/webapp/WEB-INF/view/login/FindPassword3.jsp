<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>패스워드 찾기</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous">
    </script>
    <style>
        .card-body{
            text-align: center;
            line-height: 100%;
        }
        .newPassword{
            margin-top: 2rem;
            margin-bottom: 2rem;
            padding: 0.5rem;
            color: #33AEFF ;
        }
    </style>
</head>
<body>
<div class="container d-flex flex-column">
    <div class="row align-items-center justify-content-center min-vh-100">
        <div class="col-12 col-md-8 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="mb-4">
                        <h1>새로운 패스워드</h1>
                    </div>
                    <div class="mb-3">
                        <h1 class="newPassword" id="newPassword">${newPassword}</h1>
                    </div>
                    <div class="mb-3 d-grid">
                        <button type="button" class="btn btn-danger" id="copyButton">복사</button>
                    </div>
                    <div class="mb-3 d-grid">
                        <button type="button" class="btn btn-primary" onclick="window.location.href='/loginForm'">로그인하러가기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
    const copyButton = document.getElementById('copyButton');
    const newPassword = document.getElementById('newPassword');
    copyButton.addEventListener('click', () => {
        const text = newPassword.innerText;
        navigator.clipboard.writeText(text).then(() => {
            Toast.fire({
                icon: 'success',
                title: '비밀번호 복사됨'
            })
        });
    });
</script>
</script>
</body>
</html>
