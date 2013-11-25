function seleccionarFuente() {
	
	$.ajax({
		 url: $("#contexto").val() + "admin/visitaTecnica/seleccionarFuente",
		 data: { 'fuente': $("#fuenteSeleccionada").val() },
		 success: function( data ) {
			 $("#visitaTecnicaForm").html(data);
	 	}
	 });
}

function enviarPuntajes() {
	
	$.ajax({
		 url: $("#contexto").val() + "admin/visitaTecnica/aceptarEdicion",
		 method: "POST",
		 data: $("#puntajesForm").serialize(),
		 success: function( data ) {
			 $("#visitaTecnicaForm").html(data);
	 	}
	 });
}