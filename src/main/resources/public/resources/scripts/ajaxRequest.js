function initHeader() {
    var token = $("meta[name='_csrf']").attr("content"),
        header = $("meta[name='_csrf_header']").attr("content"),
        headers = {}
    headers[header] = token
    $.ajaxSetup({
        headers: headers
    });
};

function submit() {
    $('.form').on("click", ".button__button-submit-js", function(e) {
        e.preventDefault();
        var pressedButton = $(this).parent().parent(),
            data = $(this.form).serialize();
        $('.input__error').each(function() {
            $(this).html("");
        });
        $('.input__input-error').each(function() {
            $(this).removeClass('input__input-error').addClass('input__input');
        });
        $.ajax({
            type: "POST",
            data: data,
            success: function(response){
                if(response.status =="FAIL") {
                    for (var p in response.result) {
                        if (response.result.hasOwnProperty(p)) {
                            pressedButton.find(".input__input[name='" + p + "']").addClass('input__input-error').removeClass('input__input');
                            pressedButton.find(".input__input-error[name='" + p + "']").parent().find('.input__error').html(response.result[p]);
                        }
                    }
                }
                else {
                    $('.infoBlock').html("Проверьте ваш почтовый ящик.");

                            $('.email_input').val('');
                            $('.password_input').val('');
                    var inputs = document.getElementsByTagName("input");
                    for (var i = 0; i < inputs.length; i++) {
                            inputs[i].disabled = true;
                    }
                    $(".header a").click(function(e) {
                        e.preventDefault();
                    });
                    $(".header_container").css('pointer-events','none');


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
    });
};