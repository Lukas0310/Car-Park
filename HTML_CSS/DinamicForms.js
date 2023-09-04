//funcion para cambiar el contenido de acuerdo al elemento seleccionado
function cambiarContenidoFormulario(){
    // Obtener elementos HTML
    var operacionSelect = document.getElementById("operacion");
    var crearUsuarioDiv = document.getElementById("crearUsuario");
    var borrarUsuarioDiv = document.getElementById("borrarUsuario");
    var modificarUsuarioDiv = document.getElementById("modificarUsuario");

    // Evento de cambio en la selección de operación
    operacionSelect.addEventListener("change", function () {
        // Ocultar todos los formularios
        crearUsuarioDiv.style.display = "none";
        borrarUsuarioDiv.style.display = "none";
        modificarUsuarioDiv.style.display = "none";

        // Mostrar el formulario correspondiente a la operación seleccionada
        var operacionSeleccionada = operacionSelect.value;
        if (operacionSeleccionada === "crear") {
            crearUsuarioDiv.style.display = "block";
        } else if (operacionSeleccionada === "borrar") {
            borrarUsuarioDiv.style.display = "block";
        } else if (operacionSeleccionada === "modificar") {
            modificarUsuarioDiv.style.display = "block";
        }
    });    
}

window.onload = function(){
    cambiarContenidoFormulario();
}