let page;

window.onload = function (){
    page = 0;
    myPaymentInfoRequestProcess();
}

function myPaymentInfoRequestProcess(){
    let param = new URLSearchParams();
    param.append('page', page);
    let url = '/myPage/myDonations?'+ param.toString();
    let option = {
        method: 'GET',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        }
    };
    fetch(url,option)
        .then(response => {
            if(response.ok){
                if(page == 0){
                    renderPaymentContainer();
                }
                response.json().then(jsonData => {
                    let donationInfoSummaries = JSON.parse(JSON.stringify(jsonData, null, null));
                    renderPaymentContent(donationInfoSummaries);
                    page++;
                });
            }
            else{
                response.json().then(data => {
                    let message = data.message;
                    if(message == null){
                        message = '결제 정보를 가지고 오는데 실패하였습니다'
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
}

function renderPaymentContainer(){
    let mainContainer = document.getElementById('main');
    let html = ''+
        '<table id="payment-container" class="table">\n' +
        '    <thead>\n' +
        '    <tr>\n' +
        '        <th scope="col">단체 이름</th>\n' +
        '        <th scope="col">모금함 제목</th>\n' +
        '        <th scope="col">주문 금액</th>\n' +
        '        <th scope="col">결제 날짜</th>\n' +
        '        <th scope="col">환불 날짜</th>\n' +
        '        <th scope="col">결제 상태</th>\n' +
        '        <th scope="col"></th>\n' +
        '    </tr>\n' +
        '    </thead>\n' +
        '    <tbody>\n' +
        '    </tbody>\n' +
        '</table>\n' +
        '<div class="container mt-5 mb-5">\n' +
        '    <div class="d-grid gap-2">\n' +
        '        <button id="btn-more" class="btn" type="button" onclick="myPaymentInfoRequestProcess()">더보기</button>\n' +
        '    </div>\n' +
        '</div>';
    mainContainer.innerHTML = html;
}

function renderPaymentContent(donationInfoSummaries){
    let paymentContainer = document.getElementById('payment-container');
    let contentContainer = paymentContainer.querySelector('tbody');
    for(let i=0;i<donationInfoSummaries.length;i++){
        let info = donationInfoSummaries[i];
        let newTr = document.createElement("tr");
        let buttonHtml = '';
        if(info.donationStatusKrName == '결제'){
            newTr.className = 'table-success';
            buttonHtml = '<td class="table-default"><button onclick="paymentCancleProcess(this)" orderId="{6}"  type="button" class="btn btn-danger">환불하기</button></td>';
        }
        else {
            newTr.className = 'table-danger';
            buttonHtml = '<td class="table-default" orderId="{6}"></td>';
        }
        let temp = ''+
            '<tr>\n' +
            '    <td>{0}</td>\n' +
            '    <td>{1}</td>\n' +
            '    <td>{2}</td>\n' +
            '    <td>{3}</td>\n' +
            '    <td>{4}</td>\n' +
            '    <td>{5}</td>\n' +
            buttonHtml+
            '</tr>';
        let html = temp.format(info.organizationName,info.postTitle,info.amount,info.donationCompleteDate,
            info.donationCancleDate,info.donationStatusKrName,info.orderId);
        newTr.innerHTML = html;
        contentContainer.append(newTr);
    }
}

function paymentCancleProcess(button){
    let param = new URLSearchParams();
    param.append('orderId', button.getAttribute('orderId'));
    let url = '/myPage/myDonations/cancle?'+ param.toString();
    let option = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        }
    };
    fetch(url,option)
        .then(response => {
            if(response.ok){
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: '환불이 완료되었어요',
                    showConfirmButton: false,
                    timer: 1500
                })
                window.location.href = '/myPage';
            }
            else{
                response.json().then(data => {
                    let message = data.message;
                    if(message == null){
                        message = '환불을 하는데 실패하였습니다, 다시 시도해주세요'
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
}

String.prototype.format = function () {
    let formatted = this;
    for (let arg in arguments) {
        formatted = formatted.replace("{" + arg + "}", arguments[arg]);
    }
    return formatted;
};