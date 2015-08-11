$(document).ready(function(){
    $('.js-send').click('submit', '#js-send-form' ,function(e){
        e.preventDefault();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var headers = {};
        headers[header] = token;
        doAjaxPost(e.target, headers);
    })
});
$(document).ready(function(){
    $('.ellement').click(function(){
    	var ellementId = '#' + this.id + '_sub';
    	if( $(ellementId).is(':visible') ) {
    		$(this).removeClass('head-open');
    		$(ellementId).slideUp();
    	} else {
    		$(this).addClass('head-open');
    		$(ellementId).slideDown();
    	}
    })
    $('.delete_button').click(function(){
        var parnet_sub_id = $(this).parent().attr('id');
        var parent_id = parnet_sub_id.substring(0, parnet_sub_id.length - 4);
        $('#' + parnet_sub_id).remove();
        $('#' + parent_id).remove();
    })
});