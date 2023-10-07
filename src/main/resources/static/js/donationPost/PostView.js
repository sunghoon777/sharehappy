function donateButtonClick(){
    let param = new URLSearchParams();
    let donateButton = document.getElementById('donate-button');
    param.append('postId',donateButton.getAttribute('postId'));
    let url = '/donation/form?'+param.toString();
    let option = {
        method: 'GET',
        redirect: 'follow',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
            "Redirect-Location":url
        }
    };
    fetch(url,option)
        .then(response => {
            if(response.ok){
                window.location = response.url;
            }
            else if(response.redirected){
                window.location = response.url;
            }
            else{
                throw new Error();
            }
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: '알 수없는 오류로 처리 할 수 없습니다',
                confirmButtonText: '확인'
            })
        });
}