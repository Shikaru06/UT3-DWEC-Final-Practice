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
				<a href="${pageContext.request.contextPath}/alojamientos">Catálogo</a><a
					class="btn btn--primary"
					href="${pageContext.request.contextPath}/alojamientos?accion=nuevo">Nuevo</a>
			</nav>
		</div>
	</header>
	<main>
		<section class="hero">
			<div class="container">
				<div class="hero__content">
					<span class="eyebrow">Catálogo</span>
					<h1>Espacios que encajan con tu viaje</h1>
					<p>Integración autónoma de CRUD, dos relaciones, método privado
						de mapeo, DAO, MVC, imágenes y pruebas completas.</p>
					<a class="btn btn--primary" href="#catalogo">Explorar</a>
				</div>
			</div>
		</section>
		<section class="section section--white" id="catalogo">
			<div class="container">
				<div class="section-head">
					<div>
						<span class="eyebrow">Selección</span>
						<h2>Descubre el catálogo</h2>
					</div>
					<a class="btn btn--primary"
						href="${pageContext.request.contextPath}/alojamientos?accion=nuevo">Nuevo</a>
				</div>
				<%-- TODO 1-9: completa variable, colección, imagen, título, descripción, distintivo, metadato, id y precio cuando exista. --%>
				<div class="grid">
					<c:if test="${empty alojamientos}">
						<div class="empty">
							<h3>No hay resultados</h3>
							<p>La interfaz está preparada para representar una colección
								vacía.</p>
						</div>
					</c:if>
					<c:forEach var="alojamiento" items="${alojamientos}">
						<article class="card">
							<img class="card__image"
								src="${pageContext.request.contextPath}${alojamiento.imagen}"
								alt="${alojamiento.nombre}">
							<div class="card__body">
								<span class="badge">${alojamiento.localidad.nombre}</span>
								<h3>${alojamiento.nombre}</h3>
								<p>${alojamiento.descripcion}</p>
								<div class="meta">
									<span>Capacidad : ${alojamiento.capacidad}</span>
								</div>
								<div class="card__footer">
									<strong class="price">${alojamiento.precioNoche} €</strong><a
										class="btn btn--ghost"
										href="${pageContext.request.contextPath}/alojamientos?accion=detalle&amp;id=${alojamiento.id}">Ver
										detalle</a>
								</div>
							</div>
						</article>
					</c:forEach>
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
