$(document).ready(function(){
    $('.accordion').on("click", ".accordion__head", function(){
    	var content = $(this).parent().find('.accordion__content');
        if(content.is(':visible')) {
            $(this).addClass('accordion__head--closed');
            $(this).find('.accordion__head__arrow').addClass('accordion__head__arrow--closed');
            content.slideUp();
        } else {
            $(this).removeClass('accordion__head--closed');
            $(this).find('.accordion__head__arrow').removeClass('accordion__head__arrow--closed');
            content.slideDown();
        }

     //    var ellementId = '#' + this.id + '_sub';
    	// if( $(ellementId).is(':visible') ) {
    	// 	$(this).removeClass('head-open');
    	// 	$(ellementId).slideUp();
    	// } else {
    	// 	$(this).addClass('head-open');
    	// 	$(ellementId).slideDown();
    	// }


    })
});