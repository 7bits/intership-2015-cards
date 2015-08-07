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
            $('.infoBlock').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_campaign_input').addClass("red-error");
                        $('#' + p + 'Error').html(response.result[p]);
                    }
                }
                $('.infoBlock').html("");
            }
            else {
                $('.infoBlock').html("Кампания успешно создана!");
                setTimeout(function() {
                timeoutRedirect();
                }, 1500);
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}

function timeoutRedirect() {
    window.location.replace("http://localhost:9000/storepage_new")
}