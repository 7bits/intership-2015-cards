function doAjaxPostAddCampaign() {
    var name = $('#nameCampaign').val();
    var description = $('#descriptionCampaign').val();
    var percent = $('#percentCampaign').val();
    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var headers = {};
                    headers[header] = token;
    $.ajax({
        type: "POST",
        url: "/add_campaign",
        data: "name=" + name + "&description=" + description + "&percent=" + percent,
        headers: headers,
        success: function(response){
            $('.errors').html("");
            $('.infoBlockAddCampaign').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_campaign_input').addClass("red-error");
                        $('#' + p + 'Error').html(response.result[p]);
                    }
                }
                $('.infoBlockAddCampaign').html("");
            }
            else {
                $('.infoBlock').html("Кампания успешно создана!");
                var inputs = document.getElementsByTagName("input");
                for (var i = 0; i < inputs.length; i++) {
                    inputs[i].disabled = true;
                }
                $(".header a").click(function(e) {
                    e.preventDefault();
                });
                $(".header_container").css('pointer-events','none');

                $('.infoBlockAddCampaign').html("Кампания успешно создана!");
                setTimeout(function() {
                    for (var i = 0; i < inputs.length; i++) {
                        inputs[i].disabled = false;
                    }
                    $(".header_container").css('pointer-events','');
                timeoutRedirect();
                }, 3000);
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}

function timeoutRedirect() {
    window.location.replace("http://discounts.7bits.it/store_area")
}