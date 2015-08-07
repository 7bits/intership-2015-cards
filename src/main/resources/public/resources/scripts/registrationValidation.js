$(document).ready(function(){
    $('.js-registration').on('submit', '#js-registration-form' ,function(e){
        e.preventDefault();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var headers = {};
        headers[header] = token;
        doAjaxPost(e.target, headers);
    })
});
function timeoutRedirect(){
    window.location.replace("http://discounts.7bits.it/homepage");
}

function doAjaxPost(data, headers) {
    $.ajax({
        type: "POST",
        url: "/registration",
        data: $(data).serialize(),
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlock').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_input').addClass("red-error");
                        $('#' + p + 'Error').html(response.result[p]);

                    }
                }
                $('.infoBlock').html("");
            }
            else {
                $('.infoBlock').html("Регистрация прошла успешно!");

                        $('.email_input').val('');
                        $('.password_input').val('');

                setTimeout(function() {
                    timeoutRedirect();
                }, 3000);
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}