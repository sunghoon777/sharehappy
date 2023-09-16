let submitButton;
let email;
let password;
let passwordConfirm;
let nickname;



window.onload = function (){
    //초기화
    submitButton = document.getElementById('submit-btn');
    email = document.getElementById('email');
    password = document.getElementById('password');
    passwordConfirm = document.getElementById('passwordConfirm');
    nickname = document.getElementById('nickname');

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
                case 'nickname' :
                    document.getElementById('error-nickname').innerText = fieldError.message;
                    break;
            }
        });

        if(data.globalErrorInfos.length != 0){
            document.getElementById('error-passwordConfirm').innerText = data.globalErrorInfos[0];
        }
    }

    
    //가입 버튼 이벤트 설정
    submitButton.addEventListener('click',()=>{
        let requestDate = {
            "email" : email.value,
            "password" : password.value,
            "passwordConfirm" : passwordConfirm.value,
            "nickname" : nickname.value
        }
        let url = '/signup/donor';
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
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        Swal.fire(
                            {
                                title: '회원 가입 완료',
                                text: data.nickname+'님 반가워요',
                                icon: 'success',
                                confirmButtonColor: '#3399ff',
                                confirmButtonText: '로그인 하기'
                            }
                        ).then(()=>{
                            window.location = "/login/form";
                        });
                    });
                }
                else if(response.status == 400){
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
                        Swal.fire({
                            icon: 'error',
                            title: message,
                            confirmButtonText: '확인'
                        })
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