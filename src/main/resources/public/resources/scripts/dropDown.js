$(document).ready(function() {

	$(".dropDown--js").on("click", ".dropDown--js__head", function() {

		var list = $(this).closest(".dropDown--js").find(".dropDown--js__list");
			head =  $(this).closest(".dropDown--js").find(".dropDown--js__head");

		if ( !list.is(":visible") ) {
			list.slideDown();
			head.addClass("button--info__head--open");
		} else {
			list.slideUp();
			head.removeClass("button--info__head--open");
		}

	});

});