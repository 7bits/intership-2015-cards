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
            $('.infoBlockCreateDiscountByCampaign').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_create_discount_by_campaign_input').addClass("red-error");
                        $('#' + p + 'Error').html(response.result[p]);
                    }
                }
                $('.infoBlockCreateDiscountByCampaign').html("");
            }
            else {
                $('.infoBlockCreateDiscountByCampaign').html("Скидка успешно создана!");
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}
