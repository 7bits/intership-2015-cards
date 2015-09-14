$(document).ready(function() {
    $(".discount__tab-head").on("click", ".tab-head__head", function(event) {
    	var currentTab;
    	if (! $(this).hasClass("tab-head__head--active")) {
    		$(".tab-head__head--active").removeClass("tab-head__head--active");
    		$(".tab-content__accordion--active").removeClass("tab-content__accordion--active");
	        // $(".tab-content__accordion").each(function() {
	        // 	$(this).css("display", "none");
	        // });
	        currentTab = ($(this).attr("class").split(" ")[1]);
	        currentTab = currentTab.substring(16, currentTab.length);
	        $(this).addClass("tab-head__head--active");
        	$(".tab-content__accordion--" + currentTab).addClass("tab-content__accordion--active").css;
    	}
        //16
    });
});