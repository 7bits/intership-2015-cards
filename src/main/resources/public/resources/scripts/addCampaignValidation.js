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

                $('#search_use').after("<div class=\"ellement\" id=\"ellement_" + response.result.id + "\">" + response.result.storeName + "</div>\n" +
                    "<div class=\"ellement_sub\" id=\"ellement_" + response.result.id + "_sub\">\n"
                        +"<div class=\"describe\">\n"
                            +"<div class=\"description-container\">\n"
                                +"<div class=\"text\">"
                                    +"<div class=\"description-heading\">Описание скидки:</div>"
                                +"</div>"
                                +"<div class=\"store-img-container\">"
                                    +"<img class=\"store-img\" src=\"/resources/img/mediamarkt.png\">"
                                +"</div>"
                                +"<div class=\"text\">"
                                    +response.result.description
                                +"</div>"
                            +"</div>"
                            +"<div class=\"btn\">"
                                +"<div class=\"morph-button morph-button-modal morph-button-modal-2 morph-button-fixed morph-button-use-send\">"
                                    +"<button class=\"before-morph\" type=\"button\">Отправить</button>"
                                    +"<div class=\"morph-content\">"
                                        +"<div>"
                                            +"<div class=\"content-style-form content-style-form-1\">"
                                                +"<span class=\"icon icon-close\">Закрыть окно</span>"
                                                +"<h2>Скидка</h2>"
                                                +"<form action=\"\\peonal_area\" method=\"post\">"
                                                +"<p>"
                                                    +"<label>Ключ</label>"
                                                    +"<input type=\"text\" value=\"" + response.result.key + "\">"
                                                +"</p>"
                                            +"</div>"
                                        +"</div>"
                                    +"</div>"
                                +"</div>"
                            +"</div>"
                        +"</div>"
                    +"</div>")

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