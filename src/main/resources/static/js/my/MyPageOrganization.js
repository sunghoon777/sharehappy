let page;

window.onload = function () {
    page = 0;
}

function myPostInfoRequestProcess(){
    let param = new URLSearchParams();
    param.append('page', page);
    let url = '/myPage/myDonationPosts?'+ param.toString();
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
                    renderPostContainer();
                }
                response.json().then(jsonData => {
                    let postInfoSummaries = JSON.parse(JSON.stringify(jsonData, null, null));
                    renderPostContent(postInfoSummaries);
                    page++;
                });
            }
            else{
                response.json().then(data => {
                    let message = data.message;
                    if(message == null){
                        message = '모금함 정보를 가지고 오는데 실패하였습니다'
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

function renderPostContainer(){
    let mainContainer = document.getElementById('main');
    let html = ''+
        '<div id="post-container" class="row">\n' +
        '</div>\n'+
        '<div class="container mt-5 mb-5">\n' +
        '    <div class="d-grid gap-2">\n' +
        '        <button id="btn-more" class="btn" type="button" onclick="myPostInfoRequestProcess()">더보기</button>\n' +
        '    </div>\n' +
        '</div>';
}

function renderPostContent(postInfoSummaries){
    postInfoSummaries.forEach((post)=>{
        let newDiv = document.createElement("div");
        let fundPercentage = parseInt(post.fundPercentage);
        let formattedAmount = new Intl.NumberFormat('ko-KR').format(post.currentAmount);
        let html = ''+
            '<div class="card" onclick="window.location.href=\'/donationPost/{0}\'">\n' +
            '                    <img src="{1}" class="card-img-top">\n' +
            '                    <div class="card-body container">\n' +
            '                        <p class="title mt-1">{2}</p>\n' +
            '                        <p class="organization-name mt-1">{3}</p>\n' +
            '                        <div class="progress" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">\n' +
            '                            <div class="progress-bar" style="width: {4}%"></div>\n' +
            '                        </div>\n' +
            '                        <div class="d-flex justify-content-between align-items-center">\n' +
            '                            <span class="percentage">{5}%</span>\n' +
            '                            <span>{6}원</span>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '</div>'+
            '<div class="d-flex justify-content-center align-items-center mt-3">\n' +
            '                    <button onclick="deletePost(this)" type="button" class="btn btn-danger" postId="{7}">삭제</button>\n' +
            '</div>';
        newDiv.className = "col-3 mb-4 px-1 py-1";
        newDiv.innerHTML = html.format(post.postId,post.thumbNailImageUrl,post.title,post.organizationName,
            fundPercentage,fundPercentage,formattedAmount,post.postId);
        postContainer.appendChild(newDiv);
    });
}

function deletePost(deleteButton){
    let param = new URLSearchParams();
    param.append('postId', deleteButton.getAttribute('postId'));
    let url = '/myPage/myPost/delete?'+ param.toString();
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
                    title: '삭제가 완료되었어요',
                    showConfirmButton: false,
                    timer: 1500
                })
                window.location.href = '/myPage';
            }
            else{
                response.json().then(data => {
                    let message = data.message;
                    if(message == null){
                        message = '삭제 하는데 실패했어요, 다시 시도해주세요'
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