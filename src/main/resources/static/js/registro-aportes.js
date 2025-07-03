document.addEventListener('DOMContentLoaded', function () {
    const sectorSelect = document.getElementById('sectorSelect');
    const grupoSelect = document.getElementById('grupoSelect');
    const tablaSociosBody = document.getElementById('tablaSociosBody');

    // Cuando cambia el sector, cargamos los grupos
    sectorSelect.addEventListener('change', () => {
        const sectorId = sectorSelect.value;
        grupoSelect.innerHTML = '<option value="" disabled selected>Selecciona un grupo</option>';
        tablaSociosBody.innerHTML = ''; // Limpiamos la tabla

        if (sectorId) {
            fetch(`/aportes/grupos-por-sector/${sectorId}`)
                .then(response => response.json())
                .then(grupos => {
                    console.log("Respuesta de grupos:", grupos);
                    grupos.forEach(grupo => {
                        const option = document.createElement('option');
                        option.value = grupo.id;
                        option.textContent = grupo.nombre;
                        grupoSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error al cargar grupos:', error);
                    alert('Error al cargar los grupos.');
                });
        }
    });

    // Cuando cambia el grupo, cargamos los socios
    grupoSelect.addEventListener('change', () => {
        const grupoId = grupoSelect.value;
        tablaSociosBody.innerHTML = '';

        if (grupoId) {
            fetch(`/aportes/socios-por-grupo/${grupoId}`)
                .then(response => response.json())
                .then(socios => {
                    socios.forEach(socio => {
                        const row = document.createElement('tr');

                        row.innerHTML = `
                            <td>${socio.idUsuario}</td>
                            <td>${socio.nombre}</td>
                            <td>${socio.apellido}</td>
                            <td>
                                <input type="checkbox" name="pagados" value="${socio.id}">
                            </td>
                        `;
                        tablaSociosBody.appendChild(row);
                    });
                })
                .catch(error => {
                    console.error('Error al cargar socios:', error);
                    alert('Error al cargar los socios.');
                });
        }
    });
});
