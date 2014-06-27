package ar.com.marcelomingrone.derechosAutor.estadisticas.dao;

import org.hibernate.Query;
import org.springframework.util.StringUtils;

public class DaoUtils {
	
	public static String getWhereClause(Integer trimestre, Integer anio, Long idPais, String filtro) {
		return getWhereClause(trimestre, anio, idPais, filtro, null, false, true);
	}
	
	public static String getWhereClauseExt(Integer trimestre, Integer anio, Long idPais, String filtro) {
		return getWhereClauseExt(trimestre, anio, idPais, filtro, null, false, true);
	}
	
	public static String getWhereClause(Integer trimestre, Integer anio, Long idPais, 
			String filtro, Long idAutor) {
		return getWhereClause(trimestre, anio, idPais, filtro, idAutor, false, false);
	}
	
	public static String getWhereClauseExt(Integer trimestre, Integer anio, Long idPais, 
			String filtro, Long idAutor) {
		return getWhereClauseExt(trimestre, anio, idPais, filtro, idAutor, false, false);
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
			
			if (sqlSyntax) {
				buffer.append("AND dc.pais_id = :idPais ");
				
			} else {
				buffer.append("AND dc.pais.id = :idPais ");
			}
		}
		
		if (!StringUtils.isEmpty(filtro)) {
			
			if (filtrarPorAutor) {
				buffer.append("AND dc.autor.nombre like :filtro ");
				
			} else {
				buffer.append("AND dc.cancion.nombre like :filtro ");
			}
		}
		
		if (idAutor != null) {
			buffer.append("AND dc.autor.id = :idAutor ");
		}
		
		return buffer.toString();
	}
	
	
	private static String getWhereClauseExt(Integer trimestre, Integer anio, Long idPais, String filtro,
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
	
	
	public static String getWhereClauseExt2(Integer trimestre, Integer anio, Long idPais, String filtro) {
		
		StringBuffer buffer = new StringBuffer("");
		
		if (trimestre != null) {
			buffer.append("AND trimestre = :trimestre ");
		}
		
		if (anio != null) {
			buffer.append("AND anio = :anio ");
		}
		
		if (idPais != null) {
			buffer.append("AND idPais = :idPais ");
		}
		
		if (!StringUtils.isEmpty(filtro)) {
			buffer.append("AND nombreAutor like :filtro ");
		}
		
		return buffer.toString();
	}
	
	
	public static String getWhereClauseSQL(Integer trimestre, Integer anio, Long idPais) {
		return getWhereClause(trimestre, anio, idPais, null, null, true, true);
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


	public static String getWhereClauseOrNull(Integer trimestre, Integer anio,
			Long idPais, String filtro) {
		
		StringBuffer buffer = new StringBuffer("WHERE ");
		
		if (trimestre != null) {
			buffer.append("dc.trimestre = :trimestre ");
		
		} else {
			buffer.append("dc.trimestre is null ");
		}
		
		buffer.append("AND ");
		
		if (anio != null) {
			buffer.append("dc.anio = :anio ");
		
		} else {
			buffer.append("dc.anio is null ");
		}
		
		buffer.append("AND ");
		
		if (idPais != null) {
			buffer.append("dc.pais.id = :idPais ");
			
		} else {
			buffer.append("dc.pais.id is null ");
		}
			
		if (!StringUtils.isEmpty(filtro)) {
			buffer.append("AND dc.autor.nombre like :filtro ");
		}
		
		return buffer.toString();
	}
	
	
	public static String getSelectClause(Integer trimestre, Integer anio, Long idPais) {
		
		String valorTrimestre = StringUtils.isEmpty(trimestre) ? "null" : String.valueOf(trimestre);
		String valorAnio = StringUtils.isEmpty(anio) ? "null" : String.valueOf(anio);
		String valorPais = StringUtils.isEmpty(idPais) ? "null" : String.valueOf(idPais);
		
		return valorPais + " as pais_id, " + valorTrimestre + " as trimestre, " + valorAnio + " as anio, ";
	}
	
	public static String getSelectClauseOrNull(Integer trimestre, Integer anio, Long idPais) {
		
		StringBuffer buffer = new StringBuffer();
		
		if (trimestre != null) {
			buffer.append("trimestre, ");
			
		} else {
			buffer.append("null as trimestre, ");
		}
		
		if (anio != null) {
			buffer.append("anio, ");
			
		} else {
			buffer.append("null as anio, ");
		}
		
		if (idPais != null) {
			buffer.append("idPais, ");
			
		} else {
			buffer.append("null as idPais, ");
		}
		
		return buffer.toString();
	}

	public static String getGroupByClause(Integer trimestre, Integer anio,
			Long idPais) {
		
		StringBuffer buffer = new StringBuffer();
		
		if (trimestre != null) {
			buffer.append(", trimestre ");
		}
		
		if (anio != null) {
			buffer.append(", anio ");
		}
		
		if (idPais != null) {
			buffer.append(", idPais ");
		}
		
		return buffer.toString();
	}
}
