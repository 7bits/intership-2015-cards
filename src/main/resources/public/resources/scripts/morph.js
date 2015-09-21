$(document).ready(function() {
    $(".morph-position--js").on("click", ".morph__button--js", function() { 

            var morphPosition = $(this).closest(".morph-position--js");
                morphBackground = morphPosition.find(".morph__background");
                morphContainer = morphPosition.find(".morph-container");
                morphContent = morphPosition.find(".morph-content");
                closeButton = morphPosition.find(".morph__close");
                pressedButton = $(this);

            //morph form


            // //position at the begin
            morphContainer.addClass("morph-container--open")
            morphContainer.css("top", morphPosition.position().top);
            morphContainer.css("left",  morphPosition.position().left);
            
            //morphContainer.css("position", "absolute");
            

            //pressed button
            pressedButton.addClass("morph__button--open");
            //pressedButton.css("border-bottom", "none");
            //pressedButton.css("display", "none");


            //morph window color
            morphContainer.css("background-color", $(this).css("background-color"));

            
            //morph window size
            var width = parseInt(morphContent.width()) + 2 * parseInt(morphContent.css("padding-left").replace("px",""));
                height = parseInt(morphContent.height()) + 2 * parseInt(morphContent.css("padding-top").replace("px",""));

            
            //animation of morphing
            morphContainer.animate({
                top: "50%",
                left: "50%",
                width: width,
                height: height,
                marginTop: -(height / 2),
                marginLeft: -(width / 2)
            }, 400);
                 

            //background apear
            morphBackground.delay(300).queue(function(next) { 
                $(this).addClass("morph__background--open"); 
                next();
            });       


            //morph form apear
            morphContent.delay(300).fadeIn(100);


            //close button apear
            closeButton.css("position", "absolute");
            closeButton.css("top", 0);
            closeButton.css("right", 0);
            closeButton.delay(400).fadeIn(50);
    });

    $(window).resize( function() {
        var currentElement = $(".morph__background--open").find(".morph-container");
            currentForm = currentElement.find("form");
            width = parseInt(currentForm.width()) + 2 * parseInt(currentForm.css("padding-left").replace("px",""));
            height = parseInt(currentForm.height()) + 2 * parseInt(currentForm.css("padding-top").replace("px",""));
        currentElement.css("width", width);
        currentElement.css("height", height);
        currentElement.css("margin-top", -height / 2);
        currentElement.css("margin-left", -width / 2);

    });

    $(".morph-position--js").on("click", ".morph__close", function() {

        var morphPosition = $(this).closest(".morph-position--js");
            morphContainer = morphPosition.find(".morph-container");
            morphBackground = morphPosition.find(".morph__background");
            closeButton = morphPosition.find(".morph__close");
            morphContent = morphPosition.find("form");
            pressedButton = morphPosition.find(".morph__button--js");

        morphPosition.css("pointer-events", "none");

        positionTop = morphPosition.position().top;
        positionLeft = morphPosition.position().left;
        
        closeButton.fadeOut(50);
        morphContent.delay(50).fadeOut(100);
        morphBackground.delay(150).queue(function(next) { 
            $(this).removeClass("morph__background--open"); 
            next();
        });  

        morphContainer.delay(150).animate( {
            top: positionTop,
            left: positionLeft,
            width: "120px",
            height: "40px",
            margin: 0
        }, 300);


        morphContainer.delay(0).queue(function(next) { 
            $(this).removeClass("morph-container--open"); 
            next();
        });  
        
        pressedButton.delay(450).queue(function(next) { 
            $(this).removeClass("morph__button--open");
            next();
        });

        morphPosition.delay(900).queue(function(next) {
            $(this).css("pointer-events", "auto");
            next();
        });

    });

});