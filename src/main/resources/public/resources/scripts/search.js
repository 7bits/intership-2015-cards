window.onload = function(){

$('#send-search').on('input', function(){
    var text = $(this).val();
    $('#send_tab .ellement').show();
    if($('#send_tab .ellement:not(:contains(' + text + '))').css("display")!=='none' && $('#send_tab .ellement:not(:contains(' + text + '))').next().css("display")!=='none') {
        $('#send_tab .ellement').next().show();
    }
    $('#send_tab .ellement:not(:contains(' + text + '))').hide();
    if($('#send_tab .ellement:not(:contains(' + text + '))').css("display")=='none') {
        $('#send_tab .ellement:not(:contains(' + text + '))').next().hide();
    }


});

$('#use-search').on('input', function(){
    var text = $(this).val();
    $('#use_tab .ellement').show();
    if($('#use_tab .ellement:not(:contains(' + text + '))').css("display")!=='none' && $('#use_tab .ellement:not(:contains(' + text + '))').next().css("display")!=='none') {
        $('#use_tab .ellement').next().show();
    }
    $('#use_tab .ellement:not(:contains(' + text + '))').hide();
    if($('#use_tab .ellement:not(:contains(' + text + '))').css("display")=='none') {
        $('#use_tab .ellement:not(:contains(' + text + '))').next().hide();
    }
});

}