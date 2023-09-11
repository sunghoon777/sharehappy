window.onload = function () {
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
    const copyButton = document.getElementById('copyButton');
    const newPassword = document.getElementById('newPassword');
    copyButton.addEventListener('click', () => {
        const text = newPassword.innerText;
        navigator.clipboard.writeText(text).then(() => {
            Toast.fire({
                icon: 'success',
                title: '비밀번호 복사됨'
            })
        });
    });
}