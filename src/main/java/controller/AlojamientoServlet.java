package controller;

import dao.AlojamientoDAO;
import dao.LocalidadDAO;
import dao.TipoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import model.Alojamiento;
import model.Localidad;
import model.Tipo;

@WebServlet("/alojamientos")
public class AlojamientoServlet extends HttpServlet {
	private AlojamientoDAO alojamientoDAO;
	private LocalidadDAO localidadDAO;
	private TipoDAO tipoDAO;

	@Override
	public void init() {
		alojamientoDAO = new AlojamientoDAO();
		localidadDAO = new LocalidadDAO();
		tipoDAO = new TipoDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if (accion == null || accion.isBlank())
			accion = "listar";
		try {
			switch (accion) {
			case "listar" -> listar(request, response);
			case "detalle" -> detalle(request, response);
			case "nuevo" -> mostrarNuevo(request, response);
			case "editar" -> mostrarEditar(request, response);
			default -> response.sendError(400, "Acción GET desconocida");
			}
		} catch (SQLException | IllegalArgumentException ex) {
			throw new ServletException("No se pudo atender la petición", ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String accion = request.getParameter("accion");
		try {
			if (accion == null) {
				response.sendError(400, "Falta accion");
				return;
			}
			switch (accion) {
			case "crear" -> crear(request, response);
			case "actualizar" -> actualizar(request, response);
			case "eliminar" -> eliminar(request, response);
			default -> response.sendError(400, "Acción POST desconocida");
			}
		} catch (SQLException | IllegalArgumentException ex) {
			throw new ServletException("No se pudo procesar la operación", ex);
		}
	}

	private void listar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO 1: consulta el DAO, publica la colección y realiza forward a
		// listado.jsp.
		request.setAttribute("alojamientos", alojamientoDAO.findAll());
		forward(request, response, "listado.jsp");
//        throw new UnsupportedOperationException("Pendiente listar");
	}

	private void detalle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO 2: valida id, busca Alojamiento, trata el caso inexistente y realiza
		// forward.
		int id = requiredInt(request, "id");
		Alojamiento alojamiento = alojamientoDAO.findById(id).orElse(null);
		if (alojamiento == null) {
			response.sendError(404, "No se ha encontrado alojamiento con ese ID");
			return;
		}
		request.setAttribute("alojamiento", alojamiento);
		forward(request, response, "detalle.jsp");
//        throw new UnsupportedOperationException("Pendiente detalle");
	}

	private void mostrarNuevo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		request.setAttribute("localidades", localidadDAO.findAll());
		request.setAttribute("tipos", tipoDAO.findAll());
		request.setAttribute("modo", "crear");
		forward(request, response, "formulario.jsp");
	}

	private void crear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO 3: lee y valida el formulario, crea Alojamiento, llama a insert y aplica
		// Post Redirect Get.
		Alojamiento alojamiento = readForm(request);
		alojamientoDAO.insert(alojamiento);
		response.sendRedirect(request.getContextPath() + "/alojamientos");
//		throw new UnsupportedOperationException("Pendiente crear");
	}

	private void mostrarEditar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int id = requiredInt(request, "id");
		Alojamiento alojamiento = alojamientoDAO.findById(id).orElse(null);
		if (alojamiento == null) {
			response.sendError(404, "Alojamiento no encontrado");
			return;
		}
		request.setAttribute("alojamiento", alojamiento);
		request.setAttribute("localidades", localidadDAO.findAll());
		request.setAttribute("tipos", tipoDAO.findAll());
		request.setAttribute("modo", "actualizar");
		forward(request, response, "formulario.jsp");
	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO 4: conserva el id oculto, reconstruye Alojamiento, llama a update y
		// redirige.
		int id = requiredInt(request, "id");
		Alojamiento alojamiento = readForm(request);
		alojamiento.setId(id);
		alojamientoDAO.update(alojamiento);
		response.sendRedirect(request.getContextPath() + "/alojamientos");
//        throw new UnsupportedOperationException("Pendiente actualizar");
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO 5: recibe el id por POST, elimina y redirige. Nunca elimines mediante
		// GET.
		
		alojamientoDAO.delete(Integer.parseInt(request.getParameter("id")));
		response.sendRedirect(request.getContextPath() + "/alojamientos");
//		throw new UnsupportedOperationException("Pendiente eliminar");
	}

	private Alojamiento readForm(HttpServletRequest request) throws SQLException {
		// TODO 6: reconstruye el objeto principal y sus dos relaciones a partir del
		// formulario.
		Alojamiento alojamiento = new Alojamiento();
		alojamiento.setNombre(required(request, "nombre"));
		alojamiento.setDescripcion(request.getParameter("descripcion"));
		alojamiento.setPrecioNoche(new BigDecimal(required(request, "precioNoche")));
		alojamiento.setCapacidad(requiredInt(request, "capacidad"));
		alojamiento.setImagen(required(request, "imagen"));
		alojamiento.setDestacado(request.getParameter("destacado") != null);
		Localidad localidad = localidadDAO.findById(requiredInt(request, "localidadId")).orElseThrow(() -> new IllegalArgumentException("Localidad no válido"));
        alojamiento.setLocalidad(localidad);
        Tipo tipo = tipoDAO.findById(requiredInt(request, "tipoId")).orElseThrow(() -> new IllegalArgumentException("Tipo no válido"));
        alojamiento.setTipo(tipo);
		return alojamiento;
//        throw new UnsupportedOperationException("Pendiente readForm");
	}

	private static int requiredInt(HttpServletRequest request, String name) {
		return Integer.parseInt(required(request, name));
	}

	private static String required(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null || value.isBlank())
			throw new IllegalArgumentException("Falta " + name);
		return value.trim();
	}

	private static void forward(HttpServletRequest request, HttpServletResponse response, String view)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/" + view).forward(request, response);
	}
}
