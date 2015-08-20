$(document).ready(function(){
    $('.tab-content').on("click", "div.ellement", function(){
    	var ellementId = '#' + this.id + '_sub';
    	if( $(ellementId).is(':visible') ) {
    		$(this).removeClass('head-open');
    		$(ellementId).slideUp();
    	} else {
    		$(this).addClass('head-open');
    		$(ellementId).slideDown();
    	}
    })
});