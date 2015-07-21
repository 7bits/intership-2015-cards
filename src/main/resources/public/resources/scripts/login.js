window.onload = function(){
	var myButton = document.getElementById("js-login-button");
	myButton.onclick = function(){
		var email_value = document.getElementById("js-email").value;
		var password_value = document.getElementById("js-password").value;
		if(email_value=="user" && password_value=="user"){
			document.location.href="personal_area.html";
		}
		else if(email_value=="store" && password_value=="store"){
			document.location.href="store_area.html";
		}
		else{
			document.location.href="homepage.html";
		}
	}
}