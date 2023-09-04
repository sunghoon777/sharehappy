let postSummaryRequestStatus = {
    page : 1,
    categoryName : 'all',
    postSortCriteria : 'DESCENDING_FUND_PERCENTAGE'
};
let currentCategoryButton; // 현재 카테고리 버튼
let moreButton; // 더보기 버튼
let postContainer; // 게시판 컨테이너
let sortSelect; // 정렬 선택 버튼
let categorySelect; // 카테고리 선택 버튼

String.prototype.format = function() {
    let formatted = this;
    for( let arg in arguments ) {
        formatted = formatted.replace("{" + arg + "}", arguments[arg]);
    }
    return formatted;
};

let addPostList = function (postSummaryList){
    postSummaryList.forEach((post)=>{
        let newDiv = document.createElement("div");
        let fundPercentage = parseInt(post.fundPercentage);
        let formattedAmount = new Intl.NumberFormat('ko-KR').format(post.currentAmount);
        let html = '<div class="card">\n' +
            '                    <img src="{0}" class="card-img-top">\n' +
            '                    <div class="card-body container">\n' +
            '                        <p class="title mt-1">{1}</p>\n' +
            '                        <p class="organization-name mt-1">{2}</p>\n' +
            '                        <div class="progress" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">\n' +
            '                            <div class="progress-bar" style="width: {3}%"></div>\n' +
            '                        </div>\n' +
            '                        <div class="d-flex justify-content-between align-items-center">\n' +
            '                            <span class="percentage">{4}%</span>\n' +
            '                            <span>{5}원</span>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>';
        newDiv.className = "col-3 mb-4 px-1 py-1";
        newDiv.innerHTML = html.format(post.thumbNailImageUrl,post.title,post.organizationName,fundPercentage,fundPercentage,formattedAmount);
        postContainer.appendChild(newDiv);
    });
}

let clearPostList = function (){
    while (postContainer.firstChild) {
        postContainer.removeChild(postContainer.firstChild);
    }
}

window.onload = function (){
    //초기값 설정
    currentCategoryButton = document.getElementById('all');
    currentCategoryButton.style.borderColor = 'gray';
    moreButton = document.getElementById('btn-more');
    postContainer = document.getElementById('postSummary-container');
    sortSelect = document.getElementById('sort-select');
    
    //더 보기 버튼 이벤트 설정
    moreButton.addEventListener('click',() => {
        let queryParam = new URLSearchParams(postSummaryRequestStatus).toString();
        let url = '/donationPost/donationPosts?'+queryParam;
        let option = {
            method: 'GET',
            headers: {
                "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
            }
        };
        fetch(url,option)
            .then(response => {
                if(response.ok){
                    return response.json();
                }
                else if(response.status == 404){
                    response.json().then(data => {
                        Swal.fire({
                            icon: 'error',
                            title: data.message,
                            confirmButtonText: '확인'
                        })
                    });
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
            })
            .then(jsonData => {
                let postSummaryList = JSON.parse(JSON.stringify(jsonData,null,null));
                addPostList(postSummaryList);
                postSummaryRequestStatus.page++;
            });
    });



    //정렬 기준 선택 이벤트 설정
    sortSelect.addEventListener('change',(event)=>{
        postSummaryRequestStatus.postSortCriteria = event.target.value;
        postSummaryRequestStatus.page = 0;
        let queryParam = new URLSearchParams(postSummaryRequestStatus).toString();
        let url = '/donationPost/donationPosts?'+queryParam;
        let option = {
            method: 'GET',
            headers: {
                "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
            }
        };
        fetch(url,option)
            .then(response => {
                if(response.ok){
                    return response.json();
                }
                else if(response.status == 404){
                    response.json().then(data => {
                        Swal.fire({
                            icon: 'error',
                            title: data.message,
                            confirmButtonText: '확인'
                        })
                    });
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
            })
            .then(jsonData => {
                let postSummaryList = JSON.parse(JSON.stringify(jsonData,null,null));
                clearPostList();
                addPostList(postSummaryList);
                postSummaryRequestStatus.page++;
            });
    })



    //카테고리 선택 이벤트 설정
    categorySelect = document.getElementsByClassName('category-select');
    for(let i=0;i<categorySelect.length;i++){
        categorySelect[i].addEventListener('click',()=>{
            if(categorySelect[i] != currentCategoryButton){
                postSummaryRequestStatus.categoryName = categorySelect[i].id;
                postSummaryRequestStatus.page = 0;
                let queryParam = new URLSearchParams(postSummaryRequestStatus).toString();
                let url = '/donationPost/donationPosts?'+queryParam;
                let option = {
                    method: 'GET',
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
                    }
                };
                fetch(url,option)
                    .then(response => {
                        if(response.ok){
                            return response.json();
                        }
                        else if(response.status == 404){
                            response.json().then(data => {
                                Swal.fire({
                                    icon: 'error',
                                    title: data.message,
                                    confirmButtonText: '확인'
                                })
                            });
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
                    })
                    .then(jsonData => {
                        let postSummaryList = JSON.parse(JSON.stringify(jsonData,null,null));
                        clearPostList();
                        addPostList(postSummaryList);
                        postSummaryRequestStatus.page++;
                        currentCategoryButton.style.borderColor = 'lightgrey';
                        categorySelect[i].style.borderColor = 'gray';
                        currentCategoryButton = categorySelect[i];
                    });
            }
        });
    }


}






