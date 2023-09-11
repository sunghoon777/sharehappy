window.onload = function (){
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

    let submitButton = document.getElementById('submit-btn');
    submitButton.addEventListener("click",()=>{
        let randomCode = document.getElementsByClassName('randomcode');
        let requestDate = {
            "first" : randomCode[0].value,
            "second" : randomCode[1].value,
            "third" : randomCode[2].value,
            "fourth" : randomCode[3].value,
            "fifth" : randomCode[4].value,
        }
        let url = '/findPassword/process2';
        let option = {
            method: 'POST',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json"
            },
            body : JSON.stringify(requestDate)
        };

        fetch(url,option)
            .then(response =>{
                Swal.close();
                if(response.ok){
                    response.text().then(html => {
                        document.open();
                        document.write(html);
                        document.close();
                    });
                }
                else{
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        let message = data.message;
                        if(message == null){
                            message = '알 수 없는 오류로 처리 할 수 없습니다';
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
                Swal.close();
                Swal.fire({
                    icon: 'error',
                    title: '알 수 없는 오류로 처리 할 수 없습니다',
                    confirmButtonText: '확인'
                })
            });
    });
}