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
    })
});