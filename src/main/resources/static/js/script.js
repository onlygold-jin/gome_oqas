document.querySelector('.img__btn').addEventListener('click', function () {
    document.querySelector('.content').classList.toggle('s--signup')
})

function register() {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/register",
        data: $('#register-form').serialize(),
        success: function (result) {
            console.log(result);
            if (result.status == 200) {
                alert(result.msg);
                $(':input','#register-form')
                    .not(':button, :submit, :reset, :hidden')
                    .val('')
                    .removeAttr('checked')
                    .removeAttr('selected');
            }
        },
        error: function () {
            alert("失败！");
        }
    })
}