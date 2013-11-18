function ir(url) {
	window.location.assign(url);
}

function irAbsoluto(url) {
	window.location.assign($("#contexto").val() + url);
}