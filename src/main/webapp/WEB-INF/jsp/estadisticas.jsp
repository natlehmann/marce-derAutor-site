<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp"/>


<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/estadisticas/filtrar" name="action"/>
	<jsp:param value="true" name="ocultarTrimestre"/>
</jsp:include>


<div class="ranking ranking-no-sort">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>ESTADÍSTICAS PARA ${nombrePais != null ? nombrePais : "LATINOAMERICA"}</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="referencias">
		<h4>Referencias:</h4>
		<div class="referenciaSacm"><br/></div>
		<div class="texto">SACM</div>
		<div class="referenciaOtros"><br/></div>
		<div class="texto">Distribución</div>
	</div>
	
	
	<c:forEach items="${totalesPorFuente}" var="totalPorFuente">
	
		<div class="Grid">
	
			<table class="datatable">
				<thead>
					<tr>
						<th>${totalPorFuente.fuente.nombre}</th>
						<th>1st</th>
						<th>2nd</th>
						<th>3rd</th>
						<th>4th</th>
						<th>Total</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach items="${totalPorFuente.montosPorDerecho}" var="totalPorDerecho">
						<tr class="sacm">
							<td>${totalPorDerecho.derecho.nombre}</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2"
									value="${totalPorDerecho.primerTrimestreSACM}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.segundoTrimestreSACM}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.tercerTrimestreSACM}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.cuartoTrimestreSACM}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.totalSACM}"/>
							</td>
						</tr>
						
						<tr class="otros">
							<td><br/></td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.primerTrimestreOtros}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.segundoTrimestreOtros}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.tercerTrimestreOtros}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.cuartoTrimestreOtros}"/>
							</td>
							<td class="right">
								<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorDerecho.totalOtros}"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				
				<tr class="subtotal sacm">
					<td class="titulo-sacm">TOTAL SACM</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalSACMPrimerTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalSACMSegundoTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalSACMTercerTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalSACMCuartoTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalSACM}"/>
					</td>
				</tr>
				
				<tr class="subtotal otros">
					<td class="titulo-otros">TOTAL Distribución</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalOtrosPrimerTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalOtrosSegundoTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalOtrosTercerTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalOtrosCuartoTrimestre}"/>
					</td>
					<td class="right">
						<fmt:formatNumber maxFractionDigits="2" 
								value="${totalPorFuente.totalOtros}"/>
					</td>
				</tr>
				
				<tfoot>
					<tr class="total">
						<td>TOTAL</td>
						<td class="right">
							<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorFuente.totalPrimerTrimestre}"/>
						</td>
						<td class="right">
							<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorFuente.totalSegundoTrimestre}"/>
						</td>
						<td class="right">
							<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorFuente.totalTercerTrimestre}"/>
						</td>
						<td class="right">
							<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorFuente.totalCuartoTrimestre}"/>
						</td>
						<td class="right">
							<fmt:formatNumber maxFractionDigits="2" 
									value="${totalPorFuente.total}"/>
						</td>
					</tr>
				</tfoot>
			</table>
	
		</div>
		
	</c:forEach>
	
</div>



<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />