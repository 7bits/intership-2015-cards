function doAjaxPostFeedback() {
    var title = $('#titleFeedback').val();
    var email = $('#emailFeedback').val();
    var describe = $('#describeFeedback').val();
    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var headers = {};
                    headers[header] = token;
    $.ajax({
        type: "POST",
        url: "/feedback",
        data: "title=" + title + "&email=" + email + "&describe=" + describe,
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlockFeedback').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_feedback_input').addClass("red-error");
                        $('#' + p + 'Error').html(response.result[p]);
                    }
                }
                $('.infoBlockFeedback').html("");
            }
            else {
                $('.infoBlockFeedback').html("Отзыв успешно отправлен!");
                setTimeout(function() {
                    timeoutRedirect();
                }, 1400);
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}

function timeoutRedirect() {
    window.location.replace("http://discounts.7bits.it/feedback")
}