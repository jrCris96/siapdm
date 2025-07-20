fetch('/api/total-recaudado-por-sector')
  .then(response => response.json())
  .then(data => {
    const labels = Object.keys(data);
    const values = Object.values(data);

    const backgroundColors = labels.map(() =>
      `hsl(${Math.random() * 360}, 60%, 60%)`
    );

    const ctx2 = document.getElementById('doughnut').getContext('2d');
    new Chart(ctx2, {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          label: 'Total recaudado (Bs)',
          data: values,
          backgroundColor: backgroundColors,
          borderColor: backgroundColors,
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Total Recaudado por Sector (Bs)'
          }
        }
      }
    });
  });
