window.onload = function () {
    commentButtonClickProcess();
    replyViewButtonClickProcess();
    replyButtonClickProcess();
    commentUpdateButtonClickProcess();
    deleteButtonClickProcess();
}

function setClickEventAfterPageLoad(){
    replyViewButtonClickProcess();
    replyButtonClickProcess();
    commentUpdateButtonClickProcess();
    deleteButtonClickProcess();
}

//페이지 클릭 이벤트
function pageClick(page, postId, canClick){
    if (canClick == false) {
        return;
    }
    let param = new URLSearchParams();
    param.append('page', page);
    param.append('postId', postId);
    let url = '/donationPostComment/comments?' + param.toString();
    let option = {
        method: 'GET',
        redirect: 'follow',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        }
    };
    fetch(url, option)
        .then(response => {
            if (response.ok) {
                response.json().then(jsonData => {
                    let commentsPageInfo = JSON.parse(JSON.stringify(jsonData, null, null));
                    loadCommentsPageInfo(commentsPageInfo);
                    setClickEventAfterPageLoad();
                });
            }
            else if(response.redirected){
                requestLastPage(postId);
            }
            else {
                response.json().then(data => {
                    Swal.fire({
                        icon: 'error',
                        title: data.message,
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
        })
}

function requestLastPage(postId){
    let param = new URLSearchParams();
    param.append('postId',postId);
    let url = '/donationPostComment/comments/lastPage?'+param.toString();
    let option = {
        method: 'GET',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        }
    };
    fetch(url,option)
        .then(response=>{
            if(response.ok){
                response.json().then(jsonData => {
                    let commentsPageInfo = JSON.parse(JSON.stringify(jsonData, null, null));
                    loadCommentsPageInfo(commentsPageInfo);
                    setClickEventAfterPageLoad();
                });
            }
            else{
                response.json().then(data => {
                    console.log(data.message);
                });
            }
        })
}


//댓글,페이징 정보 로딩 로직
function loadCommentsPageInfo (commentsPageInfo) {
    let container = document.getElementById('comment-container');
    let postId = container.getAttribute('postId');
    //comment list html 부분 추가
    let html = '<div class="comment-container-list">'
    let commentAccessSet = new Set();
    for(let i=0;i<commentsPageInfo.commentAccessSet.length;i++){
        commentAccessSet.add(commentsPageInfo.commentAccessSet[i]);
    }
    for (let i = 0; i < commentsPageInfo.commentSummaries.length; i++) {
        let commentSummary = commentsPageInfo.commentSummaries[i];
        let commentId = commentSummary.commentId;
        let userName = commentSummary.userName;
        let date = commentSummary.date;
        let content = commentSummary.content;
        let childCommentCount = commentSummary.childCommentCount;
        let temp = ' <div class="comment-container my-2 px-2 py-2">\n' +
            '                <div class="comment-id" style="display: none">{0}</div>'+
            '                <div class="d-flex justify-content-between mb-3">\n' +
            '                    <span class="nickname fw-semibold">{1}</span><span class="comment-date">{2}</span>\n' +
            '                </div>\n' +
            '                <p class="fw-lighter comment-content">{3}</p>'+
            '                <div class="comment-crud-container">\n' +
            '                    <button type="button" class="reply-button btn btn-outline-success btn-sm">답장</button>\n' +
            '                </div>';

        if(commentAccessSet.has(commentId)){
            temp += ' <div class="comment-crud-container">\n' +
                '      <button type="button" class="comment-update-button btn btn-outline-success btn-sm">수정</button>\n' +
                '                        <button type="button" class="comment-delete-button btn btn-outline-danger btn-sm">삭제</button>'+
                '    </div>';
        }

        if (childCommentCount != 0) {
            temp += '  <div id="{4}">\n' +
                '                                    <span class="reply-view-icon">\n' +
                '                                        <i class="fa-solid fa-chevron-up" style="color: #10c838;"></i>\n' +
                '                                    </span>\n' +
                '                        <span class="reply-view_button">\n' +
                '                                        답글 {5}개\n' +
                '                                    </span>\n' +
                '                        <div class="reply-container ms-3" load="false" >\n' +
                '\n' +
                '                        </div>\n' +
                '                    </div>';
        }
        temp += '</div>';
        html += temp.format(commentId,userName, date, content, commentId, childCommentCount);
    }
    html += '</div>';

    //pageList 부분 html 추가
    let startPage = commentsPageInfo.startPage;
    let lastPage = commentsPageInfo.lastPage;
    let currentPage = commentsPageInfo.currentPage;
    let temp = '<div class="pagination-container d-flex justify-content-center">\n' +
        '        <ul class="pagination">\n' +
        '            <li class="page-item">\n' +
        '                <span onclick="pageClick({0},{1},{2})" class="page-link pageButton" aria-label="Previous">\n' +
        '                        <span aria-hidden="true">&laquo;</span>\n' +
        '                </span>\n' +
        '            </li>';
    html += temp.format(startPage-5,postId,startPage==0?false:true);
    for (let i = startPage; i <= lastPage; i++) {
        temp = '<li class="page-item {0}">\n' +
            '                    <span onclick="pageClick({1},{2},true)" class="page-link pageButton">{3}</span>\n' +
            '   </li>';
        html += temp.format(i == currentPage ? 'active' : '', i, postId, i + 1);
    }
    temp = '  <li class="page-item">\n' +
        '                <span onclick="pageClick({0},{1},{2})" class="page-link pageButton" aria-label="Next">\n' +
        '                    <span aria-hidden="true">&raquo;</span>\n' +
        '                </span>\n' +
        '            </li>\n' +
        '        </ul>\n' +
        '    </div>\n' +
        '</div>';
    html += temp.format(lastPage + 1, postId, lastPage - startPage < 4 ? false : true);
    container.innerHTML = html;
}


//commentButton 클릭 로직 설정
function replyViewButtonClickProcess () {
    let replyButtons = document.getElementsByClassName('reply-view_button');
    for (let i = 0; i < replyButtons.length; i++) {
        replyButtons[i].addEventListener('click', () => {
            if (replyButtons[i].nextElementSibling.getAttribute('load') == 'false') {
                let commentId = replyButtons[i].parentElement.id;
                let param = new URLSearchParams();
                param.append('commentId', commentId);
                let url = '/donationPostComment/childComments?' + param.toString();
                let option = {
                    method: 'GET',
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
                    }
                };
                fetch(url, option)
                    .then(response => {
                        if (response.ok) {
                            response.json().then(jsonData => {
                                let childComments = JSON.parse(JSON.stringify(jsonData, null, null));
                                addChildComments(replyButtons[i].nextElementSibling, childComments);
                                replyButtons[i].nextElementSibling.setAttribute('load', 'true');
                            });
                        } else {
                            response.json().then(data => {
                                Swal.fire({
                                    icon: 'error',
                                    title: data.message,
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
                    })
            } else {
                if (replyButtons[i].nextElementSibling.style.display == 'block') {
                    replyButtons[i].nextElementSibling.style.display = 'none';
                } else {
                    replyButtons[i].nextElementSibling.style.display = 'block';
                }
            }
        });
    }
}

function addChildComments (container, childComments) {
    for (let i = 0; i < childComments.length; i++) {
        let userName = childComments[i].userName;
        let date = childComments[i].date;
        let content = childComments[i].content;
        let html = ' <div class="reply my-2 px-2 py-2">\n' +
            '                        <div class="d-flex justify-content-between mb-3">\n' +
            '                            <span class="nickname fw-semibold">{0}</span><span class="comment-date">{1}</span>\n' +
            '                        </div>\n' +
            '                            <p class="fw-lighter comment-content">{2}</p>\n' +
            '                           \n' +
            '                        </div>'
        container.innerHTML += html.format(userName, date, content);
    }
}

String.prototype.format = function () {
    let formatted = this;
    for (let arg in arguments) {
        formatted = formatted.replace("{" + arg + "}", arguments[arg]);
    }
    return formatted;
};
