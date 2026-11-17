package dao;

import conexion.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Alojamiento;
import model.Localidad;
import model.Tipo;

public class AlojamientoDAO {
	private static final String SELECT_ALL = "SELECT e.id AS entity_id, e.nombre AS entity_nombre, e.descripcion AS entity_descripcion, e.precio_noche AS entity_precio_noche, e.capacidad AS entity_capacidad, e.imagen AS entity_imagen, e.destacado AS entity_destacado, r1.id AS localidad_id, r1.nombre AS localidad_nombre, r2.id AS tipo_id, r2.nombre AS tipo_nombre FROM alojamiento e JOIN localidad r1 ON r1.id = e.localidad_id JOIN tipo r2 ON r2.id = e.tipo_id ORDER BY e.id";
	private static final String SELECT_ONE = "SELECT e.id AS entity_id, e.nombre AS entity_nombre, e.descripcion AS entity_descripcion, e.precio_noche AS entity_precio_noche, e.capacidad AS entity_capacidad, e.imagen AS entity_imagen, e.destacado AS entity_destacado, r1.id AS localidad_id, r1.nombre AS localidad_nombre, r2.id AS tipo_id, r2.nombre AS tipo_nombre FROM alojamiento e JOIN localidad r1 ON r1.id = e.localidad_id JOIN tipo r2 ON r2.id = e.tipo_id WHERE e.id = ?";

	public List<Alojamiento> findAll() throws SQLException {
		// TODO 1: ejecuta el SELECT con JOIN, recorre ResultSet y utiliza
		// mapearAlojamiento(...).
		List<Alojamiento> alojamientos = new ArrayList<>();

		try (Connection cn = ConnectionFactory.getConnection();
				PreparedStatement ps = cn.prepareStatement(SELECT_ALL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				alojamientos.add(mapearAlojamiento(rs));
			}
		}

		return alojamientos;
//        throw new UnsupportedOperationException("Pendiente findAll");
	}

	public Optional<Alojamiento> findById(int id) throws SQLException {
		// TODO 2: consulta por id y devuelve Optional.empty() si no existe.
		try (Connection cn = ConnectionFactory.getConnection();
				PreparedStatement ps = cn.prepareStatement(SELECT_ONE)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? Optional.of(mapearAlojamiento(rs)) : Optional.empty();
			}
		}
//        throw new UnsupportedOperationException("Pendiente findById");
	}

	public void insert(Alojamiento alojamiento) throws SQLException {
		// TODO 3: prepara INSERT, asigna todos los parámetros en el mismo orden y
		// ejecuta.
		String sql = "INSERT INTO AlOJAMIENTO (Nombre,Descripcion,Precio_Noche,Capacidad,Imagen,Destacado,Localidad_ID, Tipo_ID) Values (?,?,?,?,?,?,?,?)";
		try (Connection cn = ConnectionFactory.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
			asignarParametrosSinId(ps, alojamiento);
			ps.executeUpdate();
		}
//        throw new UnsupportedOperationException("Pendiente insert");
	}

	public void update(Alojamiento alojamiento) throws SQLException {
		// TODO 4: ejecuta UPDATE, conserva el id para la cláusula WHERE.
		String sql = """
				UPDATE ALOJAMIENTO
				SET NOMBRE = ?,
				DESCRIPCION = ?,
				PRECIO_NOCHE = ?,
				CAPACIDAD = ?,
				IMAGEN = ?,
				DESTACADO = ?,
				LOCALIDAD_ID = ?,
				TIPO_ID = ?
				WHERE ID = ?
				""";
		
		try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
    		asignarParametrosSinId(ps, alojamiento);
    		ps.setInt(9, alojamiento.getId());
    		ps.executeUpdate();
    	}
//		throw new UnsupportedOperationException("Pendiente update");
	}

	public void delete(int id) throws SQLException {
		// TODO 5: elimina por id mediante PreparedStatement.
		String sql = "DELETE FROM ALOJAMIENTO WHERE ID = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
        	
        	ps.setInt(1, id);
        	ps.executeUpdate();
            
        }
//		throw new UnsupportedOperationException("Pendiente delete");
	}

	private Alojamiento mapearAlojamiento(ResultSet rs) throws SQLException {
		Alojamiento alojamiento = new Alojamiento();
		alojamiento.setId(rs.getInt("entity_id"));
		// TODO 1: asigna la columna nombre a la propiedad nombre.
		alojamiento.setNombre(rs.getString("entity_nombre"));
		// TODO 2: asigna la columna descripcion a la propiedad descripcion.
		alojamiento.setDescripcion(rs.getString("entity_descripcion"));
		// TODO 3: asigna la columna precio_noche a la propiedad precioNoche.
		alojamiento.setPrecioNoche(rs.getBigDecimal("entity_precio_noche"));
		// TODO 4: asigna la columna capacidad a la propiedad capacidad.
		alojamiento.setCapacidad(rs.getInt("entity_capacidad"));
		// TODO 5: asigna la columna imagen a la propiedad imagen.
		alojamiento.setImagen(rs.getString("entity_imagen"));
		// TODO 6: asigna la columna destacado a la propiedad destacado.
		alojamiento.setDestacado(rs.getBoolean("entity_destacado"));
		// TODO 7: construye Localidad con los alias localidad_id y localidad_nombre.
		Localidad localidad = new Localidad();
		localidad.setId(rs.getInt("localidad_id"));
		localidad.setNombre(rs.getString("localidad_nombre"));
		alojamiento.setLocalidad(localidad);
		// TODO 8: construye Tipo con los alias tipo_id y tipo_nombre.
		Tipo tipo = new Tipo();
		tipo.setId(rs.getInt("tipo_id"));
		tipo.setNombre(rs.getString("tipo_nombre"));
		alojamiento.setTipo(tipo);
		return alojamiento;
	}

	private void asignarParametrosSinId(PreparedStatement ps, Alojamiento alojamiento) throws SQLException {
		ps.setString(1, alojamiento.getNombre());
		ps.setString(2, alojamiento.getDescripcion());
		ps.setBigDecimal(3, alojamiento.getPrecioNoche());
		ps.setInt(4, alojamiento.getCapacidad());
		ps.setString(5, alojamiento.getImagen());
		ps.setBoolean(6, alojamiento.isDestacado());
		ps.setInt(7, alojamiento.getLocalidad().getId());
		ps.setInt(8, alojamiento.getTipo().getId());
	}

}
