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
                    if (grupo && grupo.id && grupo.sectorNombre) {
                        grupoNombreSpan.textContent = grupo.nombre;
                        grupoIdInput.value = grupo.id;

                        sectorNombreSpan.textContent = grupo.sectorNombre;
                        sectorIdInput.value = grupo.sectorId;
                    } else {
                        grupoNombreSpan.textContent = "No encontrado";
                        grupoIdInput.value = "";

                        sectorNombreSpan.textContent = "No encontrado";
                        sectorIdInput.value = "";
                    }
                })
                .catch(error => {
                    grupoNombreSpan.textContent = "Error";
                    sectorNombreSpan.textContent = "Error";
                });
        }
    });
});


