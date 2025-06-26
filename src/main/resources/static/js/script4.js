document.addEventListener('DOMContentLoaded', function () {
    const sectorSelect = document.getElementById('sectorSelect');
    const grupoSelect = document.getElementById('grupoSelect');

    sectorSelect.addEventListener('change', () => {
        const sectorId = sectorSelect.value;
        grupoSelect.innerHTML = '<option value="" disabled selected>Selecciona un grupo</option>';

        if (sectorId) {
            fetch(`/usuarios/grupos-por-sector/${sectorId}`)
                .then(response => response.json())
                .then(grupos => {
                    grupos.forEach(grupo => {
                        const option = document.createElement('option');
                        option.value = grupo.id;
                        option.textContent = grupo.nombre;
                        grupoSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error al cargar grupos:', error);
                    alert('Error al cargar los grupos. Intenta nuevamente.');
                });
        }
    });
});