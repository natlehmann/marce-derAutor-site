$(document).ready(function() {

	$('.datatable').dataTable({
		"bPaginate" : false,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : false
	});

	var fechas = $("#calendar_dates").val().replace("[","").replace("]","").split(',');
	var descripciones = $("#calendar_tooltips").val().replace("[","").replace("]","").split(',');

	$('#calendar').datepicker({
		dateFormat : 'dd/mm/yy',
		beforeShowDay : highlightDays
	});
	
	$( '#calendar .ui-datepicker-calendar tbody' ).tooltip();

	function highlightDays(date) {

		for (var i = 0; i < fechas.length; i++) {
			if ( $.trim(fechas[i]) == $.trim($.datepicker.formatDate('dd/mm/yy', date)) ) {
				return [ true, 'destacada', descripciones[i].replace(/\'/g, "") ];
			} 
		}
		return [ true, '' ];
	}
});