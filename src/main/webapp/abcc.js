/**
 * 
 */// archivo javascript index.jsp
 var documento = $(document);
 
 var flag_se_consulto = false;    // flag que indica que ya se realizo una consulta de sku y hay informacion en pantalla
 var objcambioform = {"articulo_input": "", "marca_input": "", "modelo_input": "", "depto": "", "clase": "", "fam": "", "stock_input": "", "cantidad_input": "", "descontinuado": ""};     // objeto donde se guardaran los cambios realizados en el formulario
 
 documento.ready(inicializarDocument);
 
 // funcion que inicializa la pagina
function inicializarDocument()
{
	let btnConsultarSku = $("#btn_consultar_id");
	let btnaltasku = $("#btn_alta_id");
	let btnborrasku = $("#btn_baja_id");
	let btncambiosku = $("#btn_cambio_id"); 
	btnConsultarSku.click(evt_btnConsultarsku_click);
	btnaltasku.click(evt_altasku_click);
	btnborrasku.click(evt_borrasku_click);
	btncambiosku.click(evt_cambiosku_click);
	
	// se ocultan los botones no necesarios en el inicio	
	//document.getElementById("btn_baja_id").style.visibility = "hidden";
	//document.getElementById("btn_cambio_id").style.visibility = "hidden";
	
	document.getElementById("btn_baja_id").style.visibility = "hidden";
	document.getElementById("btn_cambio_id").style.visibility = "hidden";
	
	document.getElementById("date_fecalta_id").disabled = true;
	document.getElementById("date_fecbaja_id").disabled = true;
				
	$("#input_sku_id").focus();
	$('#check_desc_id').attr('title', 'Marca el SKU como descontinuado');    // tooltip check_desc_id
	
	// carga inicial combo departamentos
	consultar_departamentos();		
}
 
// evento click btnConsultar_id
function evt_btnConsultarsku_click(event)
{
	//alert("click consultar sku");
	let txt_sku = $("#input_sku_id").val();
	
	if (txt_sku.length > 0 && txt_sku.length < 7)
	{		
		valida_existe_sku(txt_sku);
		// consultar_sku(txt_sku);
	}
	else
	{
		Swal.fire({
			title: '',		       
			icon: 'warning',
			confirmButtonColor: '#3D9EF9',
			html: 'Favor de ingresar un sku valido...'
		});
		
		$("#input_sku_id").val("");
		$("#input_sku_id").focus();
	}
}

// evento click btn_alta_id
function evt_altasku_click(event)
{
	// alert("btn alta");
	// validamos el formulario
	if (!valida_alta_formulario())
	{
		return;
	}
	
	if (!valida_stock_vs_cant()) 
	{
		Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El stock debe ser mayor que cantidad',
	        didClose: () => { $("#input_stock_id").focus(); }
	    });
	    
	    return;
	}
	
	alta_sku_to_db();	
}

// evento click btn_borrar_sku
function evt_borrasku_click(event)
{
	if (!flag_se_consulto)
	{
		Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'Cargue un sku',
	        didClose: () => { $("#input_sku_id").focus(); }
	    });
	    
	    return;
	}
	
	// se valida que haya datos en el formulario
	if (!valida_alta_formulario())
	{
		return;
	}
	
	borra_sku_select($("#input_sku_id").val());
}

// evento click btn_cambio_sku (update)
function evt_cambiosku_click(event)
{
	// se valida que haya datos en el formulario
	if (!valida_alta_formulario())
	{
		return;
	}
	
	if (!valida_cambios_form())
	{
		Swal.fire({
	        title: '',		       
	        icon: 'info',
	        confirmButtonColor: '#3D9EF9',
	        html: 'No se ha realizado ningun cambio...',
	        didClose: () => { $("#input_sku_id").focus(); }
	    });
		return;
	}
	
	// se manda actualizar el sku
	actualiza_sku();		
}

// valida que se haya realizado algun cambio
function valida_cambios_form()
{	
	let check_current_value = 0;
	
	// se comparan los valores actuales con los de los guardados en objcambioform
	if (objcambioform.articulo_input != $("#input_articulo_id").val())
	{
		return true;
	}
	
	if (objcambioform.marca_input != $("#input_marca_id").val())
	{
		return true;
	}
	
	if (objcambioform.modelo_input != $("#input_modelo_id").val())
	{
		return true;
	}
	
	if (objcambioform.depto != $("#combo_dep_id").val())
	{
		return true;
	}
	
	if (objcambioform.clase != $("#combo_clase_id").val())
	{
		return true;
	}
	
	if (objcambioform.fam != $("#combo_fam_id").val())
	{
		return true;
	}
	
	if (objcambioform.stock_input != $("#input_stock_id").val())
	{
		return true;
	}
	
	if (objcambioform.cantidad_input != $("#input_cantidad_id").val())
	{
		return true;
	}
	
	// se valida un cambio sobre el check descontinuados		
	if (document.getElementById("check_desc_id").checked == true)
	{
		check_current_value = 1;
	}
				
	if (objcambioform.descontinuado != check_current_value)
	{
		return true;
	}
		
	return false;
}

// se borra el sku de la db
function borra_sku_select(skuparam)
{
	// se realiza la peticion
	$.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "borrasku", sku: skuparam}),
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria
		if (responseText == 1)
		{
				Swal.fire({
				title: '',		       
				icon: 'info',
				confirmButtonColor: '#3D9EF9',
				html: 'Se dio de baja el sku'
			});
			
			reset_form();       
		}			
	},
	error : function(xhr, status, error) {        // código a ejecutar si la petición falla
		console.log(error);
		Swal.fire({
			title: '',		       
			icon: 'error',
			confirmButtonColor: '#3D9EF9',
			html: 'Ocurri&oacute; un problema al dar de baja el sku'
		});
	}
	});
}

// se valida el form antes de dar de alta
function valida_alta_formulario()
{
	const patternOnlyLetters = new RegExp('^[A-ZÁÉÍÓÚÑ ]+$', 'i');	
	
	// input sku
	if ($("#input_sku_id").val() == "")
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo sku tiene formato incorrecto o esta vacio',
	        didClose: () => { $("#input_sku_id").val(""); $("#input_sku_id").focus(); }
	    });
	    	    
        return false;
    }
	
	// input articulo	
	if ($("#input_articulo_id").val() == "")
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo articulo no debe quedar vacio, debe ser solo texto',
	        didClose: () => { $("#input_articulo_id").focus(); }
	    });
	    	    
        return false; 
    }
         
    /*if (!patternOnlyLetters.test($("#input_articulo_id").val()))     
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo articulo debe ser solo texto',
	        didClose: () => { $("#input_articulo_id").val(""); $("#input_articulo_id").focus(); }
	    });
	    	    
        return false;
    }*/
    
    // input input_marca_id
    if ($("#input_marca_id").val() == "")
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo marca no debe quedar vacio, debe ser solo texto',
	        didClose: () => { $("#input_marca_id").focus(); }
	    });
	    	    
        return false; 
    }
    
    if (!patternOnlyLetters.test($("#input_marca_id").val()))     
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo marca debe ser solo texto',
	        didClose: () => { $("#input_marca_id").val(""); $("#input_marca_id").focus(); }
	    });
	    	    
        return false;
    }
    
    // input input_modelo_id
    if ($("#input_modelo_id").val() == "")
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo modelo no debe quedar vacio, debe ser solo texto',
	        didClose: () => { $("#input_modelo_id").focus(); }
	    });
	    	    
        return false; 
    }
    
    if (!patternOnlyLetters.test($("#input_modelo_id").val()))     
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo modelo debe ser solo texto',
	        didClose: () => { $("#input_modelo_id").val(""); $("#input_modelo_id").focus(); }
	    });
	    	    
        return false;
    }
    
    // select clase
    if ($("#combo_clase_id").val() === null)
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'Favor de seleccionar una clase',
	        didClose: () => { $("#combo_clase_id").focus(); }
	    });
	    	    
        return false;
    }
    
    // select familia
    if ($("#combo_fam_id").val() === null)
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'Favor de seleccionar una familia',
	        didClose: () => { $("#combo_fam_id").focus(); }
	    });
	    	    
        return false;
    }
    
    // input input_stock_id
    if ($("#input_stock_id").val() == "")
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo stock tiene formato incorrecto o esta vacio',
	        didClose: () => { $("#input_stock_id").val(""); $("#input_stock_id").focus(); }
	    });
	    	    
        return false;
    }
    
    // input_cantidad_id
    if ($("#input_cantidad_id").val() == "")
    {                
        Swal.fire({
	        title: '',		       
	        icon: 'error',
	        confirmButtonColor: '#3D9EF9',
	        html: 'El campo cantidad tiene formato incorrecto o esta vacio',
	        didClose: () => { $("#input_cantidad_id").val(""); $("#input_cantidad_id").focus(); }
	    });
	    	    
        return false;
    }
        
    return true;
}

// se valida que el stock sea mayor que la cantidad
function valida_stock_vs_cant()
{
	if (parseInt(document.getElementById("input_stock_id").value) > parseInt(document.getElementById("input_cantidad_id").value))
	{
		return true;
	}
	else
	{
		return false;
	}
}

// se realiza el alta del sku a la db
function alta_sku_to_db()
{
	// obtenemos los datos del formulario
	const sku_param =  $("#input_sku_id").val();
	const articulo_param =  $("#input_articulo_id").val();
	const marca_param =  $("#input_marca_id").val();
	const modelo_param =  $("#input_modelo_id").val();
	const departamento_param =  $("#combo_dep_id").val();
	const clase_param =  $("#combo_clase_id").val();
	const familia_param =  $("#combo_fam_id").val();
	const stock_param =  $("#input_stock_id").val();
	const cantidad_param =  $("#input_cantidad_id").val();
	
	// se realiza la peticion
	$.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "altasku", sku: sku_param, articulo: articulo_param, marca: marca_param,
			modelo: modelo_param, depto: departamento_param, clase: clase_param, familia: familia_param,
				stock: stock_param, cantidad: cantidad_param}),       // variable enviada a ActionServletCargaTabla
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria
			if (responseText == 1)
			{
					Swal.fire({
			        title: '',		       
			        icon: 'info',
			        confirmButtonColor: '#3D9EF9',
			        html: 'Se dio de alta el sku'
			    });
			}
			else 
			{
				Swal.fire({
			        title: '',		       
			        icon: 'warning',
			        confirmButtonColor: '#3D9EF9',
			        html: 'Ocurri&oacute; un problema al dar de alta el sku'
			    });
			}	
			
			reset_form();		
		},
		error : function(xhr, status, error) {        // código a ejecutar si la petición falla
			console.log(error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema al dar de alta el sku'
		    });
		}
	});
}

// se actualiza el sku en la db
function actualiza_sku()
{
	let check_current_value = "0";
	// obtenemos los datos del formulario
	const sku_param =  $("#input_sku_id").val();
	const articulo_param =  $("#input_articulo_id").val();
	const marca_param =  $("#input_marca_id").val();
	const modelo_param =  $("#input_modelo_id").val();
	const departamento_param =  $("#combo_dep_id").val();
	const clase_param =  $("#combo_clase_id").val();
	const familia_param =  $("#combo_fam_id").val();
	const stock_param =  $("#input_stock_id").val();
	const cantidad_param =  $("#input_cantidad_id").val();
	
	if (document.getElementById("check_desc_id").checked == true)
	{
		check_current_value = "1";
	}
	
	// se realiza la peticion
	$.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "cambiosku", sku: sku_param, articulo: articulo_param, marca: marca_param,
			modelo: modelo_param, depto: departamento_param, clase: clase_param, familia: familia_param,
				stock: stock_param, cantidad: cantidad_param, descontinuado: check_current_value}),                 // variable enviada a ActionServletCargaTabla
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria
			if (responseText == 1)
			{
					Swal.fire({
			        title: '',		       
			        icon: 'info',
			        confirmButtonColor: '#3D9EF9',
			        html: 'Se actualizo el sku'
			    });
			    
			    // se recarga el sku
			    consultar_datos_sku(sku_param);
			}
			else
			{
				Swal.fire({
			        title: '',		       
			        icon: 'info',
			        confirmButtonColor: '#3D9EF9',
			        html: 'No se actualizo el sku'
			    });
			}			
		},
		error : function(xhr, status, error) {        // código a ejecutar si la petición falla
			console.log("Error actualiza sku : " + error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema al actualizar el sku'
		    });
		}
	});
}

// se valida si el sku ya existe
function valida_existe_sku(sku_value)
{
	let ret = true;
		
	// se realiza la peticion
	$.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "validaexistesku", sku: sku_value}),    // variable enviada a ActionServletCargaTabla
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria
			if (responseText == 1)
			{
				// el sku si existe, se cargan en pantalla los datos del sku
				reset_form();
				consultar_datos_sku(sku_value);
			}
			else
			{								
				// cargar combo de departamentos
				consultar_departamentos();
				Swal.fire({
					title: '',		       
					icon: 'info',
					confirmButtonColor: '#3D9EF9',
					html: 'El sku no existe, favor de darlo de alta'
				});
				
				$("#input_articulo_id").focus();
				$("#btn_alta_id").show();       // se habilita el boton para el alta
			}	
		},
		error : function(xhr, status, error) {        // código a ejecutar si la petición falla
			console.log(error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema validar el SKU'
		    });
		}
	});
}

// se buscan los datos de un sku existente
function consultar_datos_sku(skuparam)
{
	// se realiza la peticion
	$.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'json',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "getdatosku", sku: skuparam}),
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria			
		    console.log(responseText);
		    carga_datos_sku_form(responseText);
		    document.getElementById("btn_baja_id").disabled = false;
	 		document.getElementById("btn_cambio_id").disabled = false;
		},
		error : function(error) {        // código a ejecutar si la petición falla
			console.log(error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema la obtener los datos del sku'
		    });
		}
	});
}

// carga los datos del json al formulario 
function carga_datos_sku_form(jsonresponse)
{
	 $("#input_articulo_id").val(jsonresponse.articulo_sku[0].articulo);
	 $("#input_marca_id").val(jsonresponse.articulo_sku[0].marca);
	 $("#input_modelo_id").val(jsonresponse.articulo_sku[0].modelo);
	 
	 // si los select (combos) tienen option, se los quitamos
	 $("#combo_dep_id").find("option").remove();
	 $("#combo_clase_id").find("option").remove();
	 $("#combo_fam_id").find("option").remove();
	 
	 // agregamos los datos a los select
	 $('#combo_dep_id').html("<option>" + jsonresponse.articulo_sku[0].departamento + "</option>");
	 $('#combo_clase_id').html("<option>" + jsonresponse.articulo_sku[0].clase + "</option>");
	 $('#combo_fam_id').html("<option>" + jsonresponse.articulo_sku[0].familia + "</option>");
	 
	 // input_stock_id
	 $("#input_stock_id").val(jsonresponse.articulo_sku[0].stock);
	 $("#input_cantidad_id").val(jsonresponse.articulo_sku[0].cantidad);
	 
	 // fechas
	 document.getElementById('date_fecalta_id').value = timestampToDatetimeInputString(jsonresponse.articulo_sku[0].fechaalta);
	 document.getElementById('date_fecbaja_id').value = timestampToDatetimeInputString(jsonresponse.articulo_sku[0].fechabaja);
	 
	 // descontinuado
	 // console.log("descontinuado = " + jsonresponse.articulo_sku[0].descontinuado);
	 if (jsonresponse.articulo_sku[0].descontinuado == "1")
	 {
		document.getElementById("check_desc_id").checked = true;	
	 }
	 else 
	 {
		document.getElementById("check_desc_id").checked = false;
	 }
	 
	 // se activan los botones de modificacion y baja	 	 
	 document.getElementById("btn_baja_id").style.visibility = "visible";
	 document.getElementById("btn_cambio_id").style.visibility = "visible";
	 flag_se_consulto = true;
	 
	 // se carga el objeto de cambios, con los valores actuales
	 objcambioform.articulo_input = $("#input_articulo_id").val();
	 objcambioform.marca_input = $("#input_marca_id").val();
	 objcambioform.modelo_input = $("#input_modelo_id").val();
	 objcambioform.depto = $("#combo_dep_id").val();
	 objcambioform.clase = $("#combo_clase_id").val();
	 objcambioform.fam = $("#combo_fam_id").val();
	 objcambioform.stock_input = $("#input_stock_id").val();
	 objcambioform.cantidad_input = $("#input_cantidad_id").val();	 
	 
	 if (document.getElementById("check_desc_id").checked == true)
	 	objcambioform.descontinuado = "1";
 	 else
 	 	objcambioform.descontinuado = "0";	 	 
}

function timestampToDatetimeInputString(timestamp) 
{
    const date = new Date((timestamp));
    // slice(0, 19) includes seconds
    return date.toISOString().slice(0, 19);
}

// se carga el combo de departamentos
function consultar_departamentos()
{
	// se realiza la peticion
	$.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "getdepto"}),
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria
			console.log(responseText);
			carga_combo_deptos(responseText);			
		},
		error : function(error) {        // código a ejecutar si la petición falla
			console.log(error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema al obtener los departamentos'
		    });
		}
	});
}

// carga el combo (select) de departamentos
function carga_combo_deptos(strdeptosparam)
{
	const strdeptos = strdeptosparam.replace(/[\r\n]/gm, '');   // quitamos el salto de linea y retorno de carro
	const arrayDeptos = strdeptos.split(",");
	let str_select_html = "";
	
	for (let i = 0; i < arrayDeptos.length; i++)
	{
		let select_row = arrayDeptos[i].trim();
		str_select_html += "<option>" + select_row + "</option>";
	}
	
	$('#combo_dep_id').html(str_select_html);
	document.getElementById("combo_dep_id").options.selectedIndex = -1;     // se coloca en -1 para que se pueda cargar el siguiente select sin presionar dos veces
}

// consulta las clases relacionadas con el departamento seleccionado
function consulta_clases()
{	
	 const depto = document.getElementById("combo_dep_id").value;
     
     // se realiza la peticion
     $.ajax({
		url : 'ActionServletSkuAbcc',                                 // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "getclases", departamento: depto}),
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria
			//alert(responseText);
			carga_combo_clases(responseText);			
		},
		error : function(error) {        // código a ejecutar si la petición falla
			alert(error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema la obtener las clases'
		    });
		}
	});
}

// carga el combo (select) de clases
function carga_combo_clases(strclasesparam)
{
	const strclases = strclasesparam.replace(/[\r\n]/gm, '');   // quitamos el salto de linea y retorno de carro
	const arrayClases = strclases.split(",");
	let str_select_html = "";
	
	for (let i = 0; i < arrayClases.length; i++)
	{
		let select_row = arrayClases[i].trim();
		str_select_html += "<option>" + select_row + "</option>";
	}
	
	$('#combo_clase_id').html(str_select_html);
	document.getElementById("combo_clase_id").options.selectedIndex = -1;
}

// consulta las familias relacionadas con el departamento y clase seleccionada
function consulta_familias()
{
	//alert("consulta familias");
	const depto = document.getElementById("combo_dep_id").value;
	const clase = document.getElementById("combo_clase_id").value;
	
	// se realiza la peticion
     $.ajax({
		url : 'ActionServletSkuAbcc',                      // la URL para la petición
		type : 'POST',                                                // especifica si será una petición POST o GET		
		dataType : 'text',                                            // el tipo de información que se espera de respuesta
		data: $.param({idpet: "getfamilias",departamento: depto, clase: clase}),
		success : function(responseText) {                            // código a ejecutar si la petición es satisfactoria			
			console.log("Familias encontradas : " + responseText);
			
		 	if (responseText !== "")
		 	{
				carga_combo_familias(responseText);				
		    }
			else
			{
					Swal.fire({
			        title: '',		       
			        icon: 'info',
			        confirmButtonColor: '#3D9EF9',
			        html: 'No se encontraron familias'
			    });					
			}						
		},
		error : function(error) {        // código a ejecutar si la petición falla
			alert(error);
			Swal.fire({
		        title: '',		       
		        icon: 'error',
		        confirmButtonColor: '#3D9EF9',
		        html: 'Ocurri&oacute; un problema la obtener las familias'
		    });
		}
	});
}

// carga el combo (select) de familias
function carga_combo_familias(strfamiliasparam)
{
	const strfamilia = strfamiliasparam.replace(/[\r\n]/gm, '');   // quitamos el salto de linea y retorno de carro
	const arrayFamilia = strfamilia.split(",");
	let str_select_html = "";
	
	for (let i = 0; i < arrayFamilia.length; i++)
	{
		let select_row = arrayFamilia[i].trim();
		str_select_html += "<option>" + select_row + "</option>";
	}
	
	$('#combo_fam_id').html(str_select_html);
	$("#input_sku_id").focus();
}

// funcion que reinicia los controles del formulario
function reset_form()
{	
    $("#input_articulo_id").val("");
    $("#input_marca_id").val("");
    $("#input_modelo_id").val("");
    $("#combo_dep_id").val("");
    $("#combo_clase_id").val("");
    $("#combo_fam_id").val("");
    $("#input_stock_id").val("");
    $("#input_cantidad_id").val("");
    document.getElementById("check_desc_id").checked = false;
    document.getElementById("btn_baja_id").style.visibility = "hidden";
	document.getElementById("btn_cambio_id").style.visibility = "hidden";
}
















