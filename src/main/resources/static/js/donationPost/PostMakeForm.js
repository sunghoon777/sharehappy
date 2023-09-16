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
                uploadImage(files);
            }
        }
    });

    function uploadImage(files){
        let formData = new FormData();
        for(let i=0;i<files.length;i++){
            formData.append('imageFiles',files[i]);
        }
        fetch('/donationPost/image/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    response.text().then(textData=>{
                        let data = JSON.parse(JSON.stringify(textData,null,null));
                        $('#summernote').summernote('insertImage',data, 'sss');
                    });
                } else {
                    console.error('파일 업로드 실패');
                }
            })
            .catch(error => {
                console.error('파일 업로드 오류:', error);
            });
    }
});