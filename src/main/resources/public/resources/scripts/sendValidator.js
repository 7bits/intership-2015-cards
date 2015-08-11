function doAjaxPostSend(data) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[header] = token;
    var thisId = data;
    var send_email = $('#send_email_' + thisId).val();
    var send_uin = $('#send_uin_' + thisId).val();
    var send_key = $('#send_key_' + thisId).val();
    $.ajax({
        type: "POST",
        url: "/send_discount",
        data: "uin=" + send_uin + "&email=" + send_email,
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlock').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('#send_'+p+'_'+thisId).addClass("red-error");
                        $('#' + p + 'Error' + thisId).html(response.result[p]);
                    }
                }
                $('.infoBlock').html("");
                //$('#ellement_'+thisId).remove();
                //$('#element_'+thisId+"_sub").remove();
            }
            else {
                $('#infoBlock_send_'+thisId).html("Скидка успешно отправлена!");
                $('#ellement_'+thisId+'_sub').remove();
                $('#ellement_'+thisId).remove();

            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}