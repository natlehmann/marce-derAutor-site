function seleccionarFuente() {
	
	$.ajax({
		 url: $("#contexto").val() + "admin/visitaTecnica/seleccionarFuente",
		 data: { 'fuente': $("#fuenteSeleccionada").val() },
		 success: function( data ) {
			 $("#visitaTecnicaForm").html(data);
			 
			 $(".datepicker").datepicker({
					dateFormat: 'dd/mm/yy'
			 });
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
			 
			 $(".datepicker").datepicker({
					dateFormat: 'dd/mm/yy'
			 });
	 	}
	 });
}