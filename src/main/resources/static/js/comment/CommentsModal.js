window.onload = function (){
    let commentModal = new bootstrap.Modal(document.getElementById('comment-modal'));
    
    //댓글 작성 로직
    document.getElementById('comment-button').addEventListener('click',()=>{
        commentModal.show();
    })
    
    document.getElementById('comment-modal-submit-button').addEventListener('click',()=>{
        let content = document.getElementById('comment-modal-content');
        let url = '/donationPostComment/add';
        let option = '';
        fetch(url,option)
            .then(response=>{
                if(response.ok){
                    let commentsPageInfo = JSON.parse(JSON.stringify(jsonData, null, null));
                    loadCommentsPageInfo(commentsPageInfo);
                    replyViewButtonClickProcess();
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

