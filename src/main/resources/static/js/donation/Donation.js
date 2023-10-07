window.onload = function (){
    let donationAmountInput = document.getElementById('donation-amount');
    let donateButton = document.getElementById('donate-button');

    donationAmountInput.addEventListener('keyup',()=>{
        let value = donationAmountInput.value.replace(/[^0-9]/g, '');
        let formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        donationAmountInput.value = formattedValue;
    })

    donateButton.addEventListener('click',()=>{
        let amount = donationAmountInput.value.replace(/,/g, '');
        let postId = donateButton.getAttribute('postId');
        let readyDonateResponse  = readyDonate(postId,amount);
        donate(readyDonateResponse);
    });


}
function readyDonate(postId,amount){
    let requestDate = {
        "postId" : postId,
        "amount" : amount
    }
    let url = '/donation/prepare';
    let option = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
            "Accept" : "application/json"
        },
        body : JSON.stringify(requestDate)
    }
    return fetch(url,option)
        .then(response => {
            if(response.ok){
               return response.json();
            }
            else if(response.status == 400){
                response.json().then(jsonData => {
                    let data = JSON.parse(JSON.stringify(jsonData,null,null));
                    handleReadyDonate400Error(data);
                })
            }
            else{
                response.json().then(jsonData => {
                    let data = JSON.parse(JSON.stringify(jsonData,null,null));
                    let message = data.message;
                    if(message == null){
                        data.message = '결제 요청을 생성해내는데 실패하였습니다'
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
                title: '결제 요청을 생성해내는데 실패하였습니다',
                confirmButtonText: '확인'
            })
        })
}

function donate(readyDonateResponse){
    readyDonateResponse.then(jsonData => {
            let data = JSON.parse(JSON.stringify(jsonData,null,null));
            let email = data.email;
            let userName = data.userName;
            let postTitle = data.postTitle;
            let organizationName = data.organizationName;
            let donationOrderId = data.donationOrderId;
            let amount = data.amount;
            console.log(email+" "+userName+" "+postTitle+" "+organizationName+" "+donationOrderId+" "+amount);
            IMP.init('imp66160886');
            IMP.request_pay({
                pg : 'nice',
                pay_method : 'card',
                merchant_uid: donationOrderId, //상점에서 생성한 고유 주문번호
                name : organizationName+'_'+postTitle,
                amount : amount,
                buyer_email : email,
                buyer_name : userName
            }, function(rsp) {
                if(rsp.success){
                    donateSuccessAfterProcess(rsp.merchant_uid,rsp.imp_uid);
                }
                else{
                    donateFailAfterProcess();
                }
            });
    })
}

function donateSuccessAfterProcess(orderId,pgOrderId){
    let requestDate = {
        "orderId" : orderId,
        "pgOrderId" : pgOrderId
    }
    let url = '/donation/verify';
    let option = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
            "Accept" : "application/json"
        },
        body : JSON.stringify(requestDate)
    }
    fetch(url,option)
        .then(response => {
            if(response.ok){
                Swal.fire({
                    icon: 'success',
                    title: '모금을 해주셔서 감사합니다!!',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = '/myPage';
                    }
                });
            }
            else{
                response.json().then(jsonData => {
                    let data = JSON.parse(JSON.stringify(jsonData,null,null));
                    let message = data.message;
                    if(message == null){
                        message = '결제를 실패하였습니다 만약 환불이 되지 않았다면 사이트 이메일에 문의를 남겨주세요'
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
                title: '결제를 실패하였습니다',
                confirmButtonText: '확인'
            })
        })
}

function donateFailAfterProcess(){
    Swal.fire({
        icon: 'error',
        title: '결제를 실패하였습니다',
        confirmButtonText: '확인'
    })
}

//400 status 에러 처리
function handleReadyDonate400Error (data){
    let message = '';
    data.fieldErrorInfos.forEach((fieldError)=>{
        switch (fieldError.name){
            case 'postId' :
                message += fieldError.message;
                break;
            case 'amount' :
                message += fieldError.message;
                break;
        }
    });
    document.getElementById('error').innerText = message;
}