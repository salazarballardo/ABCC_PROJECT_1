<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ABCC</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="estilos.css">
<!--jQuery library file -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!--JS file -->
<script type="text/javascript" src="abcc.js"></script>
</head>
<body>
	<!--barra de navegacion (header)-->
	<nav
		style="background: rgb(0, 100, 174) !important; padding: 15px 1px; box-shadow: 1px 3px 3px rgb(0 0 0/ 46%); margin-bottom: 1%;">
		<table>
			<tr>
				<td><img src="Imagenes/logoCoppel.png" width="250" height="60"
					style="background: #0160a4ed; border-radius: 3px; margin: 1px;">
				</td>
				<td>
					<h1 id="h1_id">ABCC</h1>
				</td>
			</tr>
		</table>
	</nav>
	<div id="container_form">

		<div class="row">
			<div class="col-25">
				<label for="input_sku"> SKU </label>
			</div>
			<div class="col-35">
				<input type="number" class="form-control" placeholder=""
					aria-label="Username" aria-describedby="basic-addon1" min="0"
					step="1" name="input_sku" id="input_sku_id">
			</div>

			<div class="col-35">
				<input type="checkbox" name="check_desc" id="check_desc_id"
					value="Descontinuados" /> <label for="check_desc">
					Descontinuados </label>
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="input_articulo"> Articulo </label>
			</div>
			<div class="col-75">
				<input type="text" class="form-control" placeholder=""
					aria-label="Username" aria-describedby="basic-addon1"
					maxlength="15" name="input_articulo" id="input_articulo_id">
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="input_marca"> Marca </label>
			</div>
			<div class="col-75">
				<input type="text" class="form-control" placeholder=""
					aria-label="Username" aria-describedby="basic-addon1"
					maxlength="15" name="input_marca" id="input_marca_id">
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="input_modelo"> Modelo </label>
			</div>
			<div class="col-75">
				<input type="text" class="form-control" placeholder=""
					aria-label="Username" aria-describedby="basic-addon1"
					maxlength="20" name="input_modelo" id="input_modelo_id">
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="combo_dep"> Departamento </label>
			</div>
			<div class="col-75">
				<select class="form-select" onchange="consulta_clases()"
					name="combo_dep" id="combo_dep_id">
				</select>
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="combo_clase"> Clase </label>
			</div>
			<div class="col-75">
				<select class="form-select" onchange="consulta_familias()"
					name="combo_clase" id="combo_clase_id">
				</select>
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="combo_familia"> Familia </label>
			</div>
			<div class="col-75">
				<select class="form-select" name="combo_fam" id="combo_fam_id">
				</select>
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="input_stock"> Stock </label>
			</div>
			<div class="col-20">
				<input type="number" class="form-control" placeholder=""
					aria-label="Username" aria-describedby="basic-addon1"
					max="999999999" name="input_stock" id="input_stock_id">
			</div>
			<div class="col-20">
				<label for="input_cantidad"> Cantidad </label>
			</div>
			<div class="col-20">
				<input type="number" class="form-control" placeholder=""
					aria-label="Username" aria-describedby="basic-addon1"
					max="999999999" name="input_cantidad" id="input_cantidad_id">
			</div>
		</div>

		<div class="row">
			<div class="col-25">
				<label for="date_fecAlta"> Fecha alta </label>
			</div>
			<div class="col-25">
				<input type="datetime-local" id="date_fecalta_id"
					name="date_fecalta">
			</div>
			<div class="col-25">
				<label for="date_fecbaja"> Fecha baja </label>
			</div>
			<div class="col-25">
				<input type="datetime-local" id="date_fecbaja_id"
					name="date_fecbaja">
			</div>
		</div>

		<div class="row">
			<div class="col-23">
				<button type="button" class="btn btn-primary btn-block"
					name="btn_alta" id="btn_alta_id">Alta</button>
			</div>
			<div class="col-23">
				<button type="button" class="btn btn-danger btn-block"
					name="btn_baja" id="btn_baja_id">Baja</button>
			</div>
			<div class="col-23">
				<button type="button" class="btn btn-secondary btn-block"
					name="btn_cambio" id="btn_cambio_id">Cambio</button>
			</div>
			<div class="col-23">
				<button type="button" class="btn btn-primary btn-block"
					name="btn_consultar" id="btn_consultar_id">Consulta SKU</button>
			</div>
		</div>

	</div>
</body>
</html>