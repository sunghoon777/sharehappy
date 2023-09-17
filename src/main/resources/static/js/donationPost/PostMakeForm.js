$(document).ready(function () {
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
        fetch('/donationPost/image/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        $('#summernote').summernote('insertImage',data, 'sss');
                    });
                }
                else if(response.status == 400){
                    response.json().then(jsonData=>{
                        let data = JSON.parse(JSON.stringify(jsonData,null,null));
                        handle400Error(data);
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

    //400 status 에러 처리
    let handle400Error = function (data){
        let errorMessage = '';
        data.fieldErrorInfos.forEach((fieldError)=>{
            errorMessage += fieldError.message;
        });
        Swal.fire({
            icon: 'error',
            title: errorMessage,
            confirmButtonText: '확인'
        })
    }
});