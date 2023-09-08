const listaDePisos = [
    { id: 1, nombre: "Piso 1" },
    { id: 2, nombre: "Piso 2" },
    { id: 3, nombre: "Piso 3" }
];

function buscarYEliminarPiso() {
    const idInput = document.getElementById("idInput").value;
    const resultado = document.getElementById("resultado");

    const pisoEncontrado = listaDePisos.find(piso => piso.id === parseInt(idInput));

    if (pisoEncontrado) {
        const indice = listaDePisos.indexOf(pisoEncontrado);
        listaDePisos.splice(indice, 1);

        resultado.innerHTML = `El piso con ID ${pisoEncontrado.id} (${pisoEncontrado.nombre}) ha sido eliminado.`;
    } else {
        resultado.innerHTML = "Piso no encontrado.";
    }
}
