function commentButtonClickProcess(){
    let commentModal = new bootstrap.Modal(document.getElementById('comment-modal'));
    //댓글 작성 로직
    document.getElementById('comment-button').addEventListener('click',()=>{
        commentModal.show();
    })

    document.getElementById('comment-modal-submit-button').addEventListener('click',()=>{
        let content = document.getElementById('comment-modal-content');
        let container = document.getElementById('comment-container');
        let postId = container.getAttribute('postId');
        let requestDate = {
            "content" : content.value,
            "postId" : postId
        }
        let url = '/donationPostComment/add';
        let redirectLocation = "/donationPost/"+postId;
        let option = {
            method: 'POST',
            redirect: 'follow',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json",
                "Redirect-Location" : redirectLocation
            },
            body : JSON.stringify(requestDate)
        };
        commentModal.hide();
        fetch(url,option)
            .then(response=>{
                if(response.redirected){
                    window.location.href = response.url;
                    return;
                }
                else if(response.ok){
                    requestLastPage(postId);
                }
                else{
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
    })
}

function replyButtonClickProcess(){
    let replyModal = new bootstrap.Modal(document.getElementById('reply-modal'));
    let replyButtons = document.getElementsByClassName('reply-button');
    for(let i=0;i<replyButtons.length;i++){
        replyButtons[i].addEventListener("click", function() {
            let container = replyButtons[i].closest(".comment-container");
            let commentId = container.querySelector('.comment-id').textContent;
            let nickname = container.querySelector('.nickname').textContent;
            document.getElementById('reply-modal-comment-id').value = commentId;
            document.getElementById('reply-modal-recipient-name').value = nickname;
            document.getElementById('reply-modal-content').value = '';
            replyModal.show();
        });
    }
    document.getElementById('reply-modal-submit-button').onclick = ()=>{
        let commentId = document.getElementById('reply-modal-comment-id').value;
        let content = document.getElementById('reply-modal-content').value;
        let postId = document.getElementById('comment-container').getAttribute('postId');
        let requestDate = {
            "content" : content,
            "parentId" : commentId,
            "postId" : postId
        }
        let url = '/donationPostComment/reply/add';
        let redirectLocation = "/donationPost/"+postId;
        let option = {
            method: 'POST',
            redirect: 'follow',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json",
                "Redirect-Location" : redirectLocation
            },
            body : JSON.stringify(requestDate)
        };

        replyModal.hide();

        fetch(url,option)
            .then(response=>{
                if(response.redirected){
                    window.location.href = response.url;
                    return;
                }
                else if(response.ok){
                    let pageItems = document.getElementsByClassName("page-item");
                    for(let i=0;i<pageItems.length;i++){
                        if(pageItems[i].classList.contains('active')){
                            pageItems[i].querySelector('span').click();
                            break;
                        }
                    }
                }
                else{
                    response.json().then(data => {
                        Swal.fire({
                            icon: 'error',
                            title: data.message,
                            confirmButtonText: '확인'
                        })
                    });
                }
            })
            .catch(error=>{
                Swal.fire({
                    icon: 'error',
                    title: '알 수없는 오류로 처리 할 수 없습니다',
                    confirmButtonText: '확인'
                })
            })
    }
}


function commentUpdateButtonClickProcess(){
    let updateModal = new bootstrap.Modal(document.getElementById('update-modal'));
    let updateButtons = document.getElementsByClassName('comment-update-button');
    for(let i=0;i<updateButtons.length;i++){
        updateButtons[i].addEventListener("click", function() {
            let container = updateButtons[i].closest(".comment-container");
            let commentId = container.querySelector('.comment-id').textContent;
            let content = container.querySelector('.comment-content').textContent;
            document.getElementById('update-modal-comment-id').value = commentId;
            document.getElementById('update-modal-content').value = content;
            updateModal.show();
        });
    }

    document.getElementById('update-modal-submit-button').onclick = ()=>{
        let commentId = document.getElementById('update-modal-comment-id').value;
        let content = document.getElementById('update-modal-content').value;
        let postId = document.getElementById('comment-container').getAttribute('postId');
        let requestDate = {
            "content" : content,
            "commentId" : commentId
        }
        let url = '/donationPostComment/update';
        let option = {
            method: 'PATCH',
            redirect: 'follow',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json"
            },
            body : JSON.stringify(requestDate)
        };
        updateModal.hide();
        fetch(url,option)
            .then(response=>{
                if(response.ok){
                    let pageItems = document.getElementsByClassName("page-item");
                    for(let i=0;i<pageItems.length;i++){
                        if(pageItems[i].classList.contains('active')){
                            pageItems[i].querySelector('span').click();
                            break;
                        }
                    }
                }
                else{
                    response.json().then(data => {
                        Swal.fire({
                            icon: 'error',
                            title: data.message,
                            confirmButtonText: '확인'
                        })
                    });
                }
            })
            .catch(error=>{
                Swal.fire({
                    icon: 'error',
                    title: '알 수없는 오류로 처리 할 수 없습니다',
                    confirmButtonText: '확인'
                })
            })
    }
}

function deleteButtonClickProcess(){
    let deleteButtons = document.getElementsByClassName('comment-delete-button');
    for(let i=0;i<deleteButtons.length;i++){
        deleteButtons[i].addEventListener("click", function() {
            let container = deleteButtons[i].closest(".comment-container");
            let commentId = container.querySelector('.comment-id').textContent;
            let param = new URLSearchParams();
            param.append('commentId',commentId);
            let url = '/donationPostComment/remove?'+param.toString();
            let option = {
                method: 'DELETE',
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
                }
            };
            fetch(url,option)
                .then(response => {
                    if(response.ok){
                        let pageItems = document.getElementsByClassName("page-item");
                        for(let i=0;i<pageItems.length;i++){
                            if(pageItems[i].classList.contains('active')){
                                pageItems[i].querySelector('span').click();
                                break;
                            }
                        }
                    }
                    else{
                        response.json().then(data => {
                            Swal.fire({
                                icon: 'error',
                                title: data.message,
                                confirmButtonText: '확인'
                            })
                        });
                    }
                })
                .catch(error=>{
                    Swal.fire({
                        icon: 'error',
                        title: '알 수없는 오류로 처리 할 수 없습니다',
                        confirmButtonText: '확인'
                    })
                })

        });
    }
}


