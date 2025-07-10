document.addEventListener("DOMContentLoaded", function () {
    const sectorSelect = document.getElementById("sectorSelect");
    const grupoSelect = document.getElementById("grupoSelect");

    if (sectorSelect && grupoSelect) {
        sectorSelect.addEventListener("change", function () {
            const sectorId = this.value;

            // Limpiar el select de grupos
            grupoSelect.innerHTML = '<option value="">Todos</option>';

            if (!sectorId) return;

            fetch('/amonestaciones/porSector/' + sectorId)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al obtener grupos");
                    }
                    return response.json();
                })
                .then(data => {
                    data.forEach(grupo => {
                        const option = document.createElement("option");
                        option.value = grupo.id;
                        option.textContent = grupo.nombre;
                        grupoSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error al cargar los grupos:', error);
                });
        });
    }
});

