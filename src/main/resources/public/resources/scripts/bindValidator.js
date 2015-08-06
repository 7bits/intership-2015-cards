function doAjaxPostBind() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[header] = token;
    var uin=$('#bind-uin').val();
    $.ajax({
        type: "POST",
        url: "/bind_discount",
        data: "uin=" + uin,
        headers: headers,
        success: function(response){
            $('.errorsBind').html("");
            $('.infoBlockBind').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_bind_input').addClass("red-error");
                        $('#' + p + 'ErrorBind').html(response.result[p]);
                    }
                }
                $('.infoBlockBind').html("");
            }
            else {
                $('.infoBlockBind').html("Скидка успешно добавлена!");
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}