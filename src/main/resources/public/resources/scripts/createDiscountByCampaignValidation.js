function doAjaxPostCreateDiscountByCampaign() {
    var email = $('#discountEmail').val();
    var name = $('#discountName').val();
    var description = $('#discountDescription').val();
    var percent = $('#discountPercent').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[header] = token;
    $.ajax({
        type: "POST",
        url: "/create_discount_by_campaign",
        data: "name=" + name + "&description=" + description + "&percent=" + percent + "&email=" + email,
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlock').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_create_discount_by_campaign_input').addClass("red-error");
                        $('#' + p + 'Error').html(response.result[p]);
                    }
                }
                $('.infoBlock').html("");
            }
            else {
                $('.infoBlock').html("Скидка успешно создана!");
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
    window.location.replace("http://discounts.7bits.it/storepage_new")
}