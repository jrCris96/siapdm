function obtenerUltimos12Meses() {
    const meses = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
    const hoy = new Date();
    let labels = [];

    for (let i = 11; i >= 0; i--) {
        let fecha = new Date(hoy.getFullYear(), hoy.getMonth() - i, 1);
        labels.push(meses[fecha.getMonth()]);
    }

    return labels;
}

document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/aportes-mensuales-por-sector')
        .then(response => response.json())
        .then(data => {
            const labels = obtenerUltimos12Meses();
            const datasets = [];

            for (let sector in data) {
                datasets.push({
                    label: sector,
                    data: data[sector],
                    fill: false,
                    borderWidth: 2,
                    tension: 0.3,
                    borderColor: `hsl(${Math.random() * 360}, 70%, 50%)`
                });
            }

            const ctx = document.getElementById('lineChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: { labels: labels, datasets: datasets },
                options: {
                    responsive: true,
                    interaction: { mode: 'index', intersect: false },
                    plugins: {
                        title: { display: true, text: 'Aportes por Sector' }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: { display: true, text: 'Monto en Bs' }
                        }
                    }
                }
            });
        });
});
