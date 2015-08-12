function doAjaxPostBind() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[header] = token;
    var uin=$('#bind-uin').val();
    $.ajax({
        type: "POST",
        url: "/bind_discount",
        data: "uin=" + uin,
        headers: headers,
        success: function(response){
            $('.errorsBind').html("");
            $('.infoBlockBind').html("");
            $('.form-control').removeClass("red-error");
            if(response.status =="FAIL") {
                for (var p in response.result) {
                    if (response.result.hasOwnProperty(p)) {
                        $('.'+p+'_bind_input').addClass("red-error");
                        $('#' + p + 'ErrorBind').html(response.result[p]);
                    }
                }
                $('.infoBlockBind').html("");
            }
            else {
                $('.infoBlockBind').html("Скидка успешно добавлена!");
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
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}

//
// +"<div class=\"js-send\">"
//                                                +"<form id=\"js-send-form\" action=\"send_discount\" method=\"post\">"
//                                                    +"<input type=\"hidden\" name=\"" + _csrf.name +"\" value=\"" + _csrf.token + "\">"
//                                                    +"<p>"
//                                                        +"<label>Пользователь</label>"
//                                                        +"<div class=\"infoBlock\" id=\"infoBlock_send_" + response.result.id + "\">"
//                                                        +"<input class=\"form-control\" id=\"send_key_" + response.result.id + "\" name=\"key\" type=\"hidden\" value=\"" + response.result.key + "\">"
//                                                        +"<input class=\"form-control\" id=\"send_uin_" + response.result.id + "\" name=\"uin\" type=\"hidden\" value=\"" + response.result.uin + "\">"
//                                                        +"<input class=\"form-control email_input\" id=\"send_email_" + response.result.id + "\" name=\"key\" type=\"hidden\" value=\"" + response.result.key + "\">"