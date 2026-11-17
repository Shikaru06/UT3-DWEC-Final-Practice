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
				<%-- TODO 1-10: propiedades visuales, id y campos hidden según las instrucciones. --%>
				<div class="detail">
					<img class="detail__image"
						src="${pageContext.request.contextPath}${alojamiento.imagen}" alt="${alojamiento.nombre}">
					<div>
						<span class="badge">${alojamiento.localidad.nombre}</span>
						<h1>${alojamiento.nombre}</h1>
						<p>${alojamiento.descripcion}</p>
						<div class="meta">
							<span>Capacidad: ${alojamiento.capacidad}</span>
						</div>
						<p class="price">${alojamiento.precioNoche}€</p>
						<div class="actions">
							<a class="btn btn--ghost"
								href="${pageContext.request.contextPath}/alojamientos">Volver</a><a
								class="btn btn--primary"
								href="${pageContext.request.contextPath}/alojamientos?accion=editar&amp;id=${alojamiento.id}">Editar</a>
							<form method="POST"
								action="${pageContext.request.contextPath}/alojamientos">
								<input type="hidden" name="accion" value="eliminar"><input
									type="hidden" name="id" value="${alojamiento.id}">
								<button class="btn btn--danger" type="submit">Eliminar</button>
							</form>
						</div>
					</div>
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
