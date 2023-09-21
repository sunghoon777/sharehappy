$(document).ready(function () {
    let title = document.getElementById('title');
    let content = document.getElementById('summernote');
    let category = document.getElementById('category ');
    let thunbnail = document.getElementById('thumbnail');
    let targetAmount = document.getElementById('targetAmount');
    let endDate = document.getElementById('endDate');
    
    //이미지 업로드 코드
    $('#summernote').summernote({
        placeholder: '내용을 입력해주세요',
        tabsize: 2,
        height: 400,
        lang: 'ko-KR',
        toolbar: [
            ['style', ['style']],
            ['fontsize', ['fontsize']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert', ['picture', 'link']]
        ],
        callbacks : {
            onImageUpload : function (files){
                for(let i=0;i<files.length;i++){
                    uploadImage(files[i]);
                }
            }
        }
    });
    
    function uploadImage(file){
        let formData = new FormData();
        formData.append('imageFile',file);
        fetch('/donationPost/temp/image/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        $('#summernote').summernote('insertImage',data.imageUri, 'sss');
                    });
                }
                else if(response.status == 400){
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        let errorMessage = '';
                        data.fieldErrorInfos.forEach((fieldError)=>{
                            errorMessage += fieldError.message;
                        });
                        Swal.fire({
                            icon: 'error',
                            title: errorMessage,
                            confirmButtonText: '확인'
                        })
                    });
                }
                else {
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        let message = data.message;
                        if(message == null){
                            message = '파일 업로드 실패';
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
                console.error('파일 업로드 오류:', error);
            });
    }

    targetAmount.addEventListener('keyup',()=>{
        let value = targetAmount.value.replace(/[^0-9]/g, '');
        let formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원';
        targetAmount.value = formattedValue;
    })

    document.getElementById('submit-button').addEventListener('click',()=>{
        let formData = new FormData();
        let unformattedTargetAmount = targetAmount.value.replaceAll(',','').replace('원',   '');
        formData.append('title',title.value);
        formData.append('content',content.value);
        formData.append('category',category.value);
        formData.append('thumbnail',thunbnail.files[0]);
        formData.append('targetAmount',unformattedTargetAmount);
        formData.append('endDate',endDate.value);
        let url = '/donationPost/make/post';
        let option = {
            method: 'POST',
            body: formData
        };
        fetch(url,option)
            .then(response => {
                if(response.ok){
                    window.location = '/main';
                }
                else if(response.status == 400){
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        handle400Error(data);
                    });
                }
                else{
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        let message = data.message;
                        if(message == null){
                            message = '알 수없는 오류로 처리 할 수 없습니다';
                        }
                        document.getElementById('error').innerText = message;
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
    });

    //400 status 에러 처리
    let handle400Error = function (data){
        let errorView = Array.from(document.getElementsByClassName('error'));
        errorView.forEach((view) => {
            view.innerText = '';
        });
        data.fieldErrorInfos.forEach((fieldError)=>{
            switch (fieldError.name){
                case 'title' :
                    document.getElementById('title-error').innerText = fieldError.message;
                    break;
                case 'content' :
                    document.getElementById('content-error').innerText = fieldError.message;
                    break;
                case 'category' :
                    document.getElementById('category-error').innerText = fieldError.message;
                    break;
                case 'thumbnail' :
                    document.getElementById('thumbnail-error').innerText = fieldError.message;
                    break;
                case 'targetAmount' :
                    document.getElementById('targetAmount-error').innerText = fieldError.message;
                    break;
                case 'endDate' :
                    document.getElementById('endDate-error').innerText = fieldError.message;
                    break;
            }
        });
    }
});