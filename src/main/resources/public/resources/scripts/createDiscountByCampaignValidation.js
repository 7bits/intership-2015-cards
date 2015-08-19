function doAjaxPostCreateDiscountByCampaign(thisId) {
    var email = $('#discount_email_' + thisId).val();
    var name = $('#discountName_' + thisId).val();
    var description = $('#discountDescription_' + thisId).val();
    var percent = $('#discountPercent_' + thisId).val();
    var backerPercent = $('#discountBackerPercent_' + thisId).val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[header] = token;
    //
    $.ajax({
        type: "POST",
        url: "/create_discount_by_campaign",
        data: "name=" + name + "&description=" + description + "&percent=" + percent + "&email=" + email + "&backerPercent=" + backerPercent,
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlock').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('#discount_'+p+'_'+thisId).addClass("red-error");
                        $('#' + p + 'ErrorActive_'+thisId).html(response.result[p]);
                    }
                }
                $('.infoBlock').html("");
            }
            else {
                $('#infoBlock_campaign_'+thisId).html("Скидка успешно создана!");
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}
