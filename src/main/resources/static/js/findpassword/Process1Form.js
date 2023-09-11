window.onload = function (){
    let findpasswordButton = document.getElementById('findpasword-btn');
    findpasswordButton.addEventListener('click',()=>{
        let requestDate = {
            "email" : document.getElementById('email').value
        }
        let url = '/findPassword/process1';
        let option = {
            method: 'POST',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json"
            },
            body : JSON.stringify(requestDate)
        };

        Swal.fire({
            title: '로딩중',
            allowOutsideClick: false,
            allowEscapeKey: false,
            showConfirmButton: false,
            willOpen: () => {
                Swal.showLoading()
            }
        });

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