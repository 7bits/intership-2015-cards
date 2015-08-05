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
            $('.remove-error-message').html('');
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('#' + p).html(response.result[p]);
                    }
                }
            }
            else {
                //$('#info').html("<h4>"+"Скидка успешно добавлена!"+"</h4>");
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}