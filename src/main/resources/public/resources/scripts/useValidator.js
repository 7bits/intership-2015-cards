$(document).ready(function(){
    $('.js-use').on('submit', '#js-use-form' ,function(e){
        e.preventDefault();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var headers = {};
        headers[header] = token;
        doAjaxPost(e.target, headers);
    })
});

function doAjaxPost(data, headers) {
    $.ajax({
        type: "POST",
        url: "/use_discount",
        data: $(data).serialize(),
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlock').html("");
            $('.key_input').removeClass("red-error");
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
                $('.infoBlock').html("Скидка успешно использована!");
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}