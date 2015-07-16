window.onload = function(){

    $('#send-search').on('input', function(){
        var text = $(this).val();
        $('#send_tab .accordion-heading').show();
        $('#send_tab .accordion-heading:not(:contains(' + text + '))').hide();
    });

    $('#use-search').on('input', function(){
        var text = $(this).val();
        $('#use_tab .accordion-heading').show();
        $('#use_tab .accordion-heading:not(:contains(' + text + '))').hide();
    });

}
