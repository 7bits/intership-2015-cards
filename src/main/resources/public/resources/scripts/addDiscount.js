$(document).ready(function(){
    $('.js-test').on('submit', '#js-add-discount' ,function(e){
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
        url: "/add_discount",
        data: $(data).serialize(),
        headers: headers,
        success: function(response){
            $('.remove-error-message').html('');
            for (var p in response.result) {
                if( response.result.hasOwnProperty(p) ) {
                    $('#'+p).html(response.result[p]);
                }
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}