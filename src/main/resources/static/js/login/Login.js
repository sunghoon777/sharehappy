let loginButton;
let email;
let password;
let emailRemember;

window.onload = function (){
    //초기 설정
    loginButton = document.getElementById('login-button');
    email = document.getElementById('email');
    password = document.getElementById('password');
    emailRemember = document.getElementById('remember_email');

    //400 status 에러 처리
    let handle400Error = function (data){
        let errorView = Array.from(document.getElementsByClassName('error'));
        errorView.forEach((view) => {
            view.innerText = '';
        });
        data.fieldErrorInfos.forEach((fieldError)=>{
            switch (fieldError.name){
                case 'email' :
                    document.getElementById('error-email').innerText = fieldError.message;
                    break;
                case 'password' :
                    document.getElementById('error-password').innerText = fieldError.message;
                    break;
            }
        });
    }


    //로그인 이벤트 설정
    loginButton.addEventListener('click',()=>{
        let requestDate = {
            "email" : email.value,
            "password" : password.value,
            "rememberEmail" : emailRemember.checked
        }
        let url = '/login';
        let option = {
            method: 'POST',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json"
            },
            body : JSON.stringify(requestDate)
        };

        fetch(url,option)
            .then(response => {
                if(response.ok){
                    let url = document.getElementById('redirectUrl').value;
                    if(url != ''){
                        window.location = url;
                    }
                    else{
                        window.location = '/main';
                    }
                }
                if(response.status == 400){
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        handle400Error(data);
                    });
                }
                else{
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        let message = data.message;
                        if(message == null){
                            message = '알 수없는 오류로 처리 할 수 없습니다';
                        }
                        document.getElementById('error').innerText = message;
                    });
                }
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: '알 수없는 오류로 처리 할 수 없습니다',
                    confirmButtonText: '확인'
                })
            });
    });
}