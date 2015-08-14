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
                $('#search_use').after("<div class=\"ellement\" id=\"ellement_" + response.result.id + "\">" + response.result.name + "</div>\n" +
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
                                    +"Скидка: " + response.result.percent + "<br>"
                                    +response.result.description
                                +"</div>"
                            +"</div>"
                            +"<div class=\"btn\">"
                                +"<div class=\"morph-button morph-button-modal morph-button-modal-2 morph-button-fixed morph-button-use-send\">"
                                    +"<button class=\"before-morph\" type=\"button\">Сгенерировать</button>"
                                    +"<div class=\"morph-content\">"
                                        +"<div>"
                                            +"<div class=\"content-style-form content-style-form-1\">"
                                                +"<span class=\"icon icon-close\">Закрыть окно</span>"
                                                +"<h2>Скидка</h2>"
                                                +"<div class=\"js-create-discount-by-campaign\">"
                                                    +"<form id=\"js-create-discount-by-campaign-form\" action=\"/create_discount_by_campaign\" method=\"post\">"
                                                        //+"<input type=\"hidden\" name=\"" + _csrf.name +"\" value=\"" + _csrf.token + "\">"
                                                        +"<p>"
                                                            +"<div class=\"infoBlock\" id=\"infoBlock_campaign_" + response.result.id + "\"></div>"
                                                            +"<label>Email</label>"
                                                            +"<input class=\"form-control email_create_discount_by_campaign_input\" name=\"email\" type=\"text\" id=\"discountEmail_" + response.result.id + "\">"
                                                            +"<div class=\"errors\" id=\"emailErrorActive_" + response.result + "\"></div>"
                                                            +"<input class=\"form-control\" name=\"name\" value=\"" +response.result.name + "\" type=\"hidden\" id=\"discountName_" + response.result.id + "\">"
                                                            +"<input class=\"form-control\" name=\"percent\" value=\"" + response.result.percent + "\" type=\"hidden\" id=\"discountPercent_" + response.result.id + "\">"
                                                            +"<input class=\"form-control\" name=\"description\" value=\"" + response.result.description + "\" type=\"hidden\" id=\"discountDescription_" + response.result.id + "\">"
                                                        +"</p>"
                                                    +"</form>"
                                                +"</div>"
                                            +"</div>"
                                        +"</div>"
                                    +"</div>"
                                +"</div>"
                            +"</div>"
                        +"</div>"
                    +"</div>")

                $('.infoBlockAddCampaign').html("Кампания успешно создана!");
//                var inputs = document.getElementsByTagName("input");
//                for (var i = 0; i < inputs.length; i++) {
//                    inputs[i].disabled = true;
//                }
//                $(".header a").click(function(e) {
//                    e.preventDefault();
//                });
//                $(".header_container").css('pointer-events','none');
//
//                $('.infoBlockAddCampaign').html("Кампания успешно создана!");
//                setTimeout(function() {
//                    for (var i = 0; i < inputs.length; i++) {
//                        inputs[i].disabled = false;
//                    }
//                    $(".header_container").css('pointer-events','');
//                timeoutRedirect();
//                }, 3000);
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