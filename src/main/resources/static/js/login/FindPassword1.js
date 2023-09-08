window.onload = function (){
    let findpasswordButton = document.getElementById('findpasword-btn');
    findpasswordButton.addEventListener('click',()=>{
        let requestDate = {
            "email" : email.value,
            "password" : password.value,
            "rememberEmail" : emailRemember.checked
        }
        let url = '/login/findpassword1';
        let option = {
            method: 'POST',
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
                "Accept" : "application/json"
            },
            body : JSON.stringify(requestDate)
        };
    });
}