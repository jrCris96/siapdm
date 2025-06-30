document.addEventListener('DOMContentLoaded', function () {
    const idUsuarioInput = document.getElementById('idUsuario');
    const grupoNombreSpan = document.getElementById('grupoNombre');
    const sectorNombreSpan = document.getElementById('sectorNombre');
    const grupoIdInput = document.getElementById('grupoId');
    const sectorIdInput = document.getElementById('sectorId');

    idUsuarioInput.addEventListener('blur', function () {
        const idUsuario = this.value.trim();

        if (idUsuario !== "") {
            fetch(`/jefes/grupo-del-socio/${idUsuario}`)
                .then(response => response.json())
                .then(grupo => {
                    if (grupo && grupo.id && grupo.sector) {
                        grupoNombreSpan.textContent = grupo.nombre;
                        grupoIdInput.value = grupo.id;

                        sectorNombreSpan.textContent = grupo.sector.nombre;
                        sectorIdInput.value = grupo.sector.id;
                    } else {
                        grupoNombreSpan.textContent = "No encontrado";
                        grupoIdInput.value = "";

                        sectorNombreSpan.textContent = "No encontrado";
                        sectorIdInput.value = "";
                    }
                })
                .catch(error => {
                    console.error("Error al obtener grupo del socio:", error);
                    grupoNombreSpan.textContent = "Error";
                    sectorNombreSpan.textContent = "Error";
                });
        }
    });
});

