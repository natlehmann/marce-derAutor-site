package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Query;
import org.springframework.util.StringUtils;

public class DaoUtils {
	
	public static String getWhereClause(Integer trimestre, Integer anio, Long idPais, String filtro) {
		return getWhereClause(trimestre, anio, idPais, filtro, null, false, true);
	}
	
	private static String getWhereClause(Integer trimestre, Integer anio, Long idPais, String filtro,
			Long idAutor, boolean sqlSyntax, boolean filtrarPorAutor) {
		
		StringBuffer buffer = new StringBuffer("");
		
		if (trimestre != null) {
			buffer.append("AND dc.trimestre = :trimestre ");
		}
		
		if (anio != null) {
			buffer.append("AND dc.anio = :anio ");
		}
		
		if (idPais != null) {
			buffer.append("AND dc.idPais = :idPais ");
		}
		
		if (!StringUtils.isEmpty(filtro)) {
			
			if (filtrarPorAutor) {
				buffer.append("AND dc.nombreAutor like :filtro ");
				
			} else {
				buffer.append("AND dc.nombreCancion like :filtro ");
			}
		}
		
		if (idAutor != null) {
			buffer.append("AND dc.idAutor = :idAutor ");
		}
		
		return buffer.toString();
	}
	
	
	public static void setearParametros(Query query, Long idPais, Integer anio,
			Integer trimestre, String filtro) {
		
		if (trimestre != null) {
			query.setParameter("trimestre", trimestre);
		}
		
		if (anio != null) {
			query.setParameter("anio", anio);
		}
		
		if (idPais != null) {
			query.setParameter("idPais", idPais);
		}
		
		if (!StringUtils.isEmpty(filtro)) {
			query.setParameter("filtro", "%" + filtro + "%");
		}
	}
	
	public static void setearParametros(Query query, Long idPais, Integer anio,
			Integer trimestre, String filtro, Long idAutor) {
		
		if (idAutor != null) {
			query.setParameter("idAutor", idAutor);
		}
		
		setearParametros(query, idPais, anio, trimestre, filtro);
	}

	public static String getWhereClause(Integer trimestre, Integer anio,
			Long idPais, String filtro, Long idAutor) {
		
		return getWhereClause(trimestre, anio, idPais, filtro, idAutor, false, false);
	}

}
