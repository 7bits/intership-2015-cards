function doAjaxPostUse() {
var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[header] = token;
var key = $('#keyUse').val();
    $.ajax({
        type: "POST",
        url: "/use_discount",
        data: "key=" + key,
        headers: headers,
        success: function(response){
            $('.errors').html('');
            $('.infoBlockUseDiscount').html('');
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_use_input').addClass("red-error");
                        $('#' + p + "Error").html(response.result[p]);
                    }
                }
            }
            else {
                $('.infoBlockUseDiscount').html("Скидка успешно добавлена!");
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}