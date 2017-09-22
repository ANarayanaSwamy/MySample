function fetchPrevMsg() {
	try {
		var requestUrl = "http://localhost:8080/fetchPrevMsg";
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var txtBox = document.getElementById("prevMsg");
				txtBox.value= this.responseText;
			}
		};
		xhttp.open("GET", requestUrl, true);
		xhttp.send();
	} catch (err) {
		alert(err.message);
	}
	setTimeout(fetchPrevMsg, 1000);
}
