window.onload = function(){

$('#send-search').on('input', function(){
    var text = $(this).val();
    $('#send_tab .accordion-group.discount').show();
    $('#send_tab .accordion-heading .heading:not(:contains(' + text + '))').parents(".accordion-group.discount").hide();
});

$('#use-search').on('input', function(){
    var text = $(this).val();
    $('#use_tab .accordion-group.discount').show();
    $('#use_tab .accordion-heading .heading:not(:contains(' + text + '))').parents(".accordion-group.discount").hide();
});

}