$(document).ready(function() {
    initHeader();
    submit();
});






















//function timeoutRedirect(){
//    window.location.replace("http://discounts.7bits.it/homepage");
//}
//
//function doAjaxPost(data, headers) {
//    $.ajax({
//        type: "POST",
//        url: "/registration",
//        data: $(data).serialize(),
//        headers: headers,
//        success: function(response){
//            $('.errors').html("");
//            $('.infoBlock').html("");
//            $('.form-control').removeClass("red-error");
//            if(response.status =="FAIL") {
//                for (var p in response.result) {
//                    if (response.result.hasOwnProperty(p)) {
//                        $('.'+p+'_input').addClass("red-error");
//                        $('#' + p + 'Error').html(response.result[p]);
//
//                    }
//                }
//                $('.infoBlock').html("");
//            }
//            else {
//                $('.infoBlock').html("Проверьте ваш почтовый ящик.");
//
//                        $('.email_input').val('');
//                        $('.password_input').val('');
//                var inputs = document.getElementsByTagName("input");
//                for (var i = 0; i < inputs.length; i++) {
//                        inputs[i].disabled = true;
//                }
//                $(".header a").click(function(e) {
//                    e.preventDefault();
//                });
//                $(".header_container").css('pointer-events','none');
//
//
//                setTimeout(function() {
//                    for (var i = 0; i < inputs.length; i++) {
//                        inputs[i].disabled = false;
//                    }
//                    $(".header_container").css('pointer-events','');
//                    timeoutRedirect();
//                }, 3000);
//            }
//        },
//        error: function(e){
//            alert('Error: ' + e);
//        }
//    });
//}