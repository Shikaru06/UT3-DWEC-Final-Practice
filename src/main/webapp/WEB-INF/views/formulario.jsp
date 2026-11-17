<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!doctype html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>StayNest</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
	<header class="navbar">
		<div class="container">
			<a class="brand"
				href="${pageContext.request.contextPath}/alojamientos">StayNest</a>
			<nav class="navlinks">
				<a href="${pageContext.request.contextPath}/alojamientos">Catálogo</a>
			</nav>
		</div>
	</header>
	<main>
		<section class="section">
			<div class="container">
				<div class="form-shell">
					<%-- TODO numerados: completa destino, método, campos hidden, nombres y valores dinámicos sin modificar las clases CSS. --%>
					<span class="eyebrow">Gestión</span>
					<h1>Formulario de alojamiento</h1>
					<form action="${pageContext.request.contextPath}/alojamientos"
						method="POST">
						<input type="hidden" name="accion" value="${modo}"><input
							type="hidden" name="id" value="${alojamiento.id}">
						<div class="form-grid">
							<div class="field">
								<label for="nombre">Nombre</label><input id="nombre" type="text"
									name="nombre" value="${alojamiento.nombre}">
							</div>
							<div class="field field--full">
								<label for="descripcion">Descripción</label>
								<textarea id="descripcion" name="descripcion">${alojamiento.descripcion}</textarea>
							</div>
							<div class="field">
								<label for="precioNoche">Precio por noche</label><input id="precioNoche"
									type="number" name="precioNoche" value="${alojamiento.precioNoche}" step="0.01"
									min="0">
							</div>
							<div class="field">
								<label for="capacidad">Capacidad</label><input id="capacidad"
									type="number" name="capacidad" value="${alojamiento.capacidad}" min="0">
							</div>
							<div class="field">
								<label for="imagen">Ruta de imagen</label><input id="imagen"
									type="text" name="imagen" value="${alojamiento.imagen}">
							</div>
							<div class="field">
								<label for="destacado">Destacado</label><input id="destacado"
									type="checkbox" name="destacado" value="true">
							</div>
							<div class="field">
								<label for="localidad">Localidad</label><select id="localidadId"
									name="localidadId"><c:forEach var="localidad"
										items="${localidades}">
										<option value="${localidad.id}" ${alojamiento.localidad.id == localidad.id ? 'selected:' : '' }>${localidad.nombre}</option>
									</c:forEach></select>
							</div>
							<div class="field">
								<label for="tipo">Tipo</label><select id="tipoId"
									name="tipoId"><c:forEach var="tipo"
										items="${tipos}">
										<option value="${tipo.id}" ${alojamiento.tipo.id == tipo.id ? 'selected:' : '' }>${tipo.nombre}</option>
									</c:forEach></select>
							</div>
						</div>
						<div class="actions">
							<button class="btn btn--primary" type="submit">Guardar</button>
							<a class="btn btn--ghost"
								href="${pageContext.request.contextPath}/alojamientos">Cancelar</a>
						</div>
					</form>
				</div>
			</div>
		</section>
	</main>
	<footer>
		<div class="container">
			<strong>StayNest</strong>
			<p>Espacios que encajan con tu viaje. Proyecto académico DWES.</p>
		</div>
	</footer>
</body>
</html>
